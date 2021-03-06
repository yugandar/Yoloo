package com.yoloo.android.framework.delegate;

import android.support.annotation.NonNull;
import com.yoloo.android.framework.MvpPresenter;
import com.yoloo.android.framework.MvpView;

/**
 * The MvpDelegate delegateCallback that will be called from
 * {@link MvpConductorLifecycleListener}.
 * This interface must be implemented by all Conductor Controllers
 * that you want to support mosbys mvp.
 *
 * @param <V> The type getPost {@link MvpView}
 * @param <P> The type getPost {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @since 1.0
 */
public interface MvpConductorDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

  /**
   * Creates the presenter instance
   *
   * @return the created presenter instance
   */
  @NonNull P createPresenter();

  /**
   * Get the presenter. If null is returned, then a internally a new presenter instance gets
   * created
   * by calling {@link #createPresenter()}
   *
   * @return the presenter instance. can be null.
   */
  @NonNull P getPresenter();

  /**
   * Sets the presenter instance
   *
   * @param presenter The presenter instance
   */
  void setPresenter(@NonNull P presenter);

  /**
   * Get the MvpView for the presenter
   *
   * @return The view associated with the presenter
   */
  @NonNull V getMvpView();
}
