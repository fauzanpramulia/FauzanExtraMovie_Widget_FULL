// Generated code from Butter Knife. Do not modify!
package com.fauzanpramulia.favoritaplicationmovie;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding<T extends DetailActivity> implements Unbinder {
  protected T target;

  @UiThread
  public DetailActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.detailTitle = Utils.findRequiredViewAsType(source, R.id.detail_title, "field 'detailTitle'", TextView.class);
    target.detailDesk = Utils.findRequiredViewAsType(source, R.id.detail_overview, "field 'detailDesk'", TextView.class);
    target.detailRating = Utils.findRequiredViewAsType(source, R.id.detail_rating, "field 'detailRating'", TextView.class);
    target.detailTanggal = Utils.findRequiredViewAsType(source, R.id.detail_TanggalRilis, "field 'detailTanggal'", TextView.class);
    target.imgView = Utils.findRequiredViewAsType(source, R.id.detail_poster, "field 'imgView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.detailTitle = null;
    target.detailDesk = null;
    target.detailRating = null;
    target.detailTanggal = null;
    target.imgView = null;

    this.target = null;
  }
}
