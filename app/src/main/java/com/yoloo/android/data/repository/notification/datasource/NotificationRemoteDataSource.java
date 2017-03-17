package com.yoloo.android.data.repository.notification.datasource;

import com.google.api.client.http.HttpHeaders;
import com.yoloo.android.data.Response;
import com.yoloo.android.data.model.FcmRealm;
import com.yoloo.android.data.model.NotificationRealm;
import com.yoloo.android.data.repository.notification.transformer.NotificationResponseTransformer;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

import static com.yoloo.android.data.ApiManager.INSTANCE;
import static com.yoloo.android.data.ApiManager.getIdToken;

public class NotificationRemoteDataSource {

  private static NotificationRemoteDataSource instance;

  private NotificationRemoteDataSource() {
  }

  public static NotificationRemoteDataSource getInstance() {
    if (instance == null) {
      instance = new NotificationRemoteDataSource();
    }
    return instance;
  }

  public Completable registerFcmToken(FcmRealm fcm) {
    return getIdToken()
        .flatMapCompletable(idToken ->
            Completable.fromAction(() ->
                INSTANCE.getApi().devices()
                    .register(fcm.getToken())
                    .setRequestHeaders(new HttpHeaders().setAuthorization("Bearer " + idToken))
                    .execute()).subscribeOn(Schedulers.io()));
  }

  public Completable unregisterFcmToken(FcmRealm fcm) {
    return getIdToken()
        .flatMapCompletable(idToken ->
            Completable.fromAction(() ->
                INSTANCE.getApi().devices()
                    .unregister(fcm.getToken())
                    .setRequestHeaders(new HttpHeaders().setAuthorization("Bearer " + idToken))
                    .execute())
                .subscribeOn(Schedulers.io()));
  }

  public Observable<Response<List<NotificationRealm>>> list(String cursor, int limit) {
    return getIdToken()
        .flatMapObservable(idToken ->
            Observable.fromCallable(() ->
                INSTANCE.getApi().accounts()
                    .notifications()
                    .list()
                    .setCursor(cursor)
                    .setLimit(limit)
                    .setRequestHeaders(new HttpHeaders().setAuthorization("Bearer " + idToken))
                    .execute())
                .subscribeOn(Schedulers.io()))
        .filter(response -> !response.getItems().isEmpty())
        .compose(NotificationResponseTransformer.create());
  }
}
