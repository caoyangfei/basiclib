package com.flyang.base.proxy;

import android.annotation.SuppressLint;
import android.content.Context;

import com.flyang.annotation.Presenter;
import com.flyang.base.contract.IPresenter;
import com.flyang.base.contract.IView;
import com.flyang.base.presenter.BasePresenter;
import com.flyang.util.data.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @author yangfei.cao
 * @ClassName PresenterImple
 * @date 2019/7/12
 * ------------- Description -------------
 * presenter实现接口
 */
public class PresenterImple implements IProxy {

    private List<IPresenter> mPresenters;
    private IView mView;
    private Context mContext;

    public PresenterImple(Context context, IView view) {
        this.mView = view;
        this.mContext = context;
        mPresenters = new ArrayList<>();
    }

    @SuppressLint("CheckResult")
    @Override
    public void bind() {
        Field[] fields = mView.getClass().getDeclaredFields();
        if (fields.length == 0) return;

        Flowable.fromArray(fields)
                .filter(field -> {
                    Presenter annotation = field.getAnnotation(Presenter.class);
                    if (annotation == null)
                        return false;
                    return true;
                })
                .subscribe(field -> {

                    Class<? extends BasePresenter> aClass = (Class<? extends BasePresenter>) field.getType();
                    //和instanceof有点像， 只不过instance用于对象的判断，而isAssignableFrom用于class判断。
                    if (!BasePresenter.class.isAssignableFrom(aClass)) {
                        // 这个 Class 是不是继承自 BasePresenter 如果不是抛异常
                        throw new RuntimeException(aClass.getName() + " Not extends BasePresenter");
                    }
                    BasePresenter presenter = getInstance(aClass);
                    if (presenter == null)
                        presenter = ReflectUtils.on(aClass).create().get();
                    checkView(presenter);
                    presenter.onAttached(mContext, mView);
                    //代码很重要，给对象赋值
                    field.setAccessible(true);
                    field.set(mView, presenter);
                    mPresenters.add(presenter);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void unbind() {
        if (mPresenters != null) {
            Flowable.fromIterable(mPresenters)
                    .subscribe(presenter -> presenter.onDestroy());
            mPresenters.clear();
        }
        mPresenters = null;
        mView = null;
    }


    /**
     * 检测mView是否都实现了presenter所要调用的View层的接口
     * 即presenter泛型中的View接口。
     *
     * @param presenter
     */
    private void checkView(BasePresenter presenter) {
        /**
         * getGenericSuperclass()返回直接继承的父类（包含泛型参数）
         * 此处返回具体presenter父辈basePresenter<V extends IView>
         */
        Type genericSuperclass = presenter.getClass().getSuperclass().getGenericSuperclass();
        //ParameterizedType参数化类型，即泛型，它是type的一种

        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        //泛型数组
        Type[] actualTypeArguments = pt.getActualTypeArguments();
        Class viewClazz = (Class) actualTypeArguments[0];

        //拿到view层所有接口，这里指activity或fragment或viewGroup
        Class<?>[] viewClasses = mView.getClass().getInterfaces();
        boolean isImplementsView = false;
        //遍历view层接口
        for (Class viewClass : viewClasses) {
            if (viewClass.isAssignableFrom(viewClazz)) {
                //view层实现了Presenter泛型中的View接口
                isImplementsView = true;
            }
        }
        if (!isImplementsView) {
            throw new RuntimeException(mView.getClass().getSimpleName() + " must implements " + viewClazz.getName());
        }
    }

}
