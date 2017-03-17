package com.yoloo.android.data.repository.tag.datasource;

import com.yoloo.android.data.ApiManager;
import com.yoloo.android.data.Response;
import com.yoloo.android.data.model.TagRealm;
import com.yoloo.android.data.sorter.TagSorter;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

public class TagRemoteDataStore {

  private static TagRemoteDataStore instance;

  private TagRemoteDataStore() {
  }

  public static TagRemoteDataStore getInstance() {
    if (instance == null) {
      instance = new TagRemoteDataStore();
    }
    return instance;
  }

  public Observable<List<TagRealm>> list(TagSorter sorter) {
    return ApiManager.getIdToken()
        .toObservable()
        .flatMap(s -> Observable.empty());
  }

  public Observable<Response<List<TagRealm>>> list(String name, String cursor, int limit) {
    TagRealm t1 = new TagRealm()
        .setId("t1")
        .setName("interrail");

    TagRealm t2 = new TagRealm()
        .setId("t2")
        .setName("interbus");

    List<TagRealm> list = new ArrayList<>();
    list.add(t1);
    list.add(t2);

    return Observable.just(Response.create(list, null));
  }
}
