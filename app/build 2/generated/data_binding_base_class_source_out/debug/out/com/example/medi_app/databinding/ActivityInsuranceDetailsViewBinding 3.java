// Generated by view binder compiler. Do not edit!
package com.example.medi_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.medi_app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityInsuranceDetailsViewBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button AmendDetailsButton;

  @NonNull
  public final Button detailsOkBtn;

  @NonNull
  public final TextView inputEnddateTv;

  @NonNull
  public final TextView inputStartdateTv;

  @NonNull
  public final TextView policynoInputTv;

  @NonNull
  public final TextView providerInputTv;

  @NonNull
  public final TextView startDateTv;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView topbarInsuranceDetails2;

  @NonNull
  public final TextView tvEndDate;

  @NonNull
  public final TextView tvInName;

  @NonNull
  public final TextView tvPolNo;

  private ActivityInsuranceDetailsViewBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button AmendDetailsButton, @NonNull Button detailsOkBtn,
      @NonNull TextView inputEnddateTv, @NonNull TextView inputStartdateTv,
      @NonNull TextView policynoInputTv, @NonNull TextView providerInputTv,
      @NonNull TextView startDateTv, @NonNull TextView textView10,
      @NonNull TextView topbarInsuranceDetails2, @NonNull TextView tvEndDate,
      @NonNull TextView tvInName, @NonNull TextView tvPolNo) {
    this.rootView = rootView;
    this.AmendDetailsButton = AmendDetailsButton;
    this.detailsOkBtn = detailsOkBtn;
    this.inputEnddateTv = inputEnddateTv;
    this.inputStartdateTv = inputStartdateTv;
    this.policynoInputTv = policynoInputTv;
    this.providerInputTv = providerInputTv;
    this.startDateTv = startDateTv;
    this.textView10 = textView10;
    this.topbarInsuranceDetails2 = topbarInsuranceDetails2;
    this.tvEndDate = tvEndDate;
    this.tvInName = tvInName;
    this.tvPolNo = tvPolNo;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityInsuranceDetailsViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityInsuranceDetailsViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_insurance_details_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityInsuranceDetailsViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Amend_details_button;
      Button AmendDetailsButton = ViewBindings.findChildViewById(rootView, id);
      if (AmendDetailsButton == null) {
        break missingId;
      }

      id = R.id.details_ok_btn;
      Button detailsOkBtn = ViewBindings.findChildViewById(rootView, id);
      if (detailsOkBtn == null) {
        break missingId;
      }

      id = R.id.input_enddate_tv;
      TextView inputEnddateTv = ViewBindings.findChildViewById(rootView, id);
      if (inputEnddateTv == null) {
        break missingId;
      }

      id = R.id.input_startdate_tv;
      TextView inputStartdateTv = ViewBindings.findChildViewById(rootView, id);
      if (inputStartdateTv == null) {
        break missingId;
      }

      id = R.id.policyno_input_tv;
      TextView policynoInputTv = ViewBindings.findChildViewById(rootView, id);
      if (policynoInputTv == null) {
        break missingId;
      }

      id = R.id.provider_input_tv;
      TextView providerInputTv = ViewBindings.findChildViewById(rootView, id);
      if (providerInputTv == null) {
        break missingId;
      }

      id = R.id.start_date_tv;
      TextView startDateTv = ViewBindings.findChildViewById(rootView, id);
      if (startDateTv == null) {
        break missingId;
      }

      id = R.id.textView10;
      TextView textView10 = ViewBindings.findChildViewById(rootView, id);
      if (textView10 == null) {
        break missingId;
      }

      id = R.id.topbar_insurance_details2;
      TextView topbarInsuranceDetails2 = ViewBindings.findChildViewById(rootView, id);
      if (topbarInsuranceDetails2 == null) {
        break missingId;
      }

      id = R.id.tv_end_date;
      TextView tvEndDate = ViewBindings.findChildViewById(rootView, id);
      if (tvEndDate == null) {
        break missingId;
      }

      id = R.id.tv_in_name;
      TextView tvInName = ViewBindings.findChildViewById(rootView, id);
      if (tvInName == null) {
        break missingId;
      }

      id = R.id.tv_pol_no;
      TextView tvPolNo = ViewBindings.findChildViewById(rootView, id);
      if (tvPolNo == null) {
        break missingId;
      }

      return new ActivityInsuranceDetailsViewBinding((ConstraintLayout) rootView,
          AmendDetailsButton, detailsOkBtn, inputEnddateTv, inputStartdateTv, policynoInputTv,
          providerInputTv, startDateTv, textView10, topbarInsuranceDetails2, tvEndDate, tvInName,
          tvPolNo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
