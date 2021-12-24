// Generated by view binder compiler. Do not edit!
package com.example.medi_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.medi_app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityReviewBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView MedicoinQuestion;

  @NonNull
  public final TextView MedipredictQuestion;

  @NonNull
  public final View divider;

  @NonNull
  public final View divider2;

  @NonNull
  public final View divider3;

  @NonNull
  public final TextView leaveAReviewTopbar;

  @NonNull
  public final RatingBar medicoinRatingbar;

  @NonNull
  public final RatingBar medipredictRatingbar;

  @NonNull
  public final Button reviewCancelbutton;

  @NonNull
  public final TextView reviewWelcomeTextview;

  @NonNull
  public final Button submitReview;

  private ActivityReviewBinding(@NonNull ScrollView rootView, @NonNull TextView MedicoinQuestion,
      @NonNull TextView MedipredictQuestion, @NonNull View divider, @NonNull View divider2,
      @NonNull View divider3, @NonNull TextView leaveAReviewTopbar,
      @NonNull RatingBar medicoinRatingbar, @NonNull RatingBar medipredictRatingbar,
      @NonNull Button reviewCancelbutton, @NonNull TextView reviewWelcomeTextview,
      @NonNull Button submitReview) {
    this.rootView = rootView;
    this.MedicoinQuestion = MedicoinQuestion;
    this.MedipredictQuestion = MedipredictQuestion;
    this.divider = divider;
    this.divider2 = divider2;
    this.divider3 = divider3;
    this.leaveAReviewTopbar = leaveAReviewTopbar;
    this.medicoinRatingbar = medicoinRatingbar;
    this.medipredictRatingbar = medipredictRatingbar;
    this.reviewCancelbutton = reviewCancelbutton;
    this.reviewWelcomeTextview = reviewWelcomeTextview;
    this.submitReview = submitReview;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityReviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityReviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_review, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityReviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Medicoin_question;
      TextView MedicoinQuestion = ViewBindings.findChildViewById(rootView, id);
      if (MedicoinQuestion == null) {
        break missingId;
      }

      id = R.id.Medipredict_question;
      TextView MedipredictQuestion = ViewBindings.findChildViewById(rootView, id);
      if (MedipredictQuestion == null) {
        break missingId;
      }

      id = R.id.divider;
      View divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.divider2;
      View divider2 = ViewBindings.findChildViewById(rootView, id);
      if (divider2 == null) {
        break missingId;
      }

      id = R.id.divider3;
      View divider3 = ViewBindings.findChildViewById(rootView, id);
      if (divider3 == null) {
        break missingId;
      }

      id = R.id.leave_a_review_topbar;
      TextView leaveAReviewTopbar = ViewBindings.findChildViewById(rootView, id);
      if (leaveAReviewTopbar == null) {
        break missingId;
      }

      id = R.id.medicoin_ratingbar;
      RatingBar medicoinRatingbar = ViewBindings.findChildViewById(rootView, id);
      if (medicoinRatingbar == null) {
        break missingId;
      }

      id = R.id.medipredict_ratingbar;
      RatingBar medipredictRatingbar = ViewBindings.findChildViewById(rootView, id);
      if (medipredictRatingbar == null) {
        break missingId;
      }

      id = R.id.review_cancelbutton;
      Button reviewCancelbutton = ViewBindings.findChildViewById(rootView, id);
      if (reviewCancelbutton == null) {
        break missingId;
      }

      id = R.id.review_welcome_textview;
      TextView reviewWelcomeTextview = ViewBindings.findChildViewById(rootView, id);
      if (reviewWelcomeTextview == null) {
        break missingId;
      }

      id = R.id.submit_review;
      Button submitReview = ViewBindings.findChildViewById(rootView, id);
      if (submitReview == null) {
        break missingId;
      }

      return new ActivityReviewBinding((ScrollView) rootView, MedicoinQuestion, MedipredictQuestion,
          divider, divider2, divider3, leaveAReviewTopbar, medicoinRatingbar, medipredictRatingbar,
          reviewCancelbutton, reviewWelcomeTextview, submitReview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
