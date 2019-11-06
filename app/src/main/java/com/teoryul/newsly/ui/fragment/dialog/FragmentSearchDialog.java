package com.teoryul.newsly.ui.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.teoryul.newsly.R;
import com.teoryul.newsly.utils.AppConstants;

public class FragmentSearchDialog extends AppCompatDialogFragment
        implements DialogInterface.OnClickListener {

    public static final String TAG = "SEARCH_DIALOG_FRAGMENT";
    public static final int REQUEST_CODE = 442;


    @BindView(R.id.edt_input)
    EditText edtInput;

    private Unbinder unbinder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() == null) {
            dismiss();
            return super.onCreateDialog(savedInstanceState);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_search_dialog, null);

        builder.setView(view)
                .setTitle(getString(R.string.title_dialog_search_topics))
                .setNegativeButton(R.string.btn_cancel, this)
                .setPositiveButton(R.string.btn_ok, this);

        unbinder = ButterKnife.bind(this, view);

        Dialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        return dialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {

            case DialogInterface.BUTTON_POSITIVE:
                processInput();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }

    private void processInput() {
        if (getTargetFragment() != null) {
            Intent data = new Intent();
            String phrase = null;

            Editable editable = edtInput.getText();
            if (editable != null) {
                phrase = editable.toString().trim();
            }

            data.putExtra(AppConstants.INTENT_KEY_SEARCH_DIALOG_PHRASE, phrase);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        }

        dismiss();
    }
}
