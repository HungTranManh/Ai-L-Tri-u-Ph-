package ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class CallRelativeDialog extends DialogFragment implements View.OnClickListener {
    private ImageView ivBillGate, ivMark, ivNgoBaoChau, ivChipu, ivCuTrongXoay, ivRonaldo;
    private boolean ischeck;
    private TextView tvAnswer;
    private int trueCase, answerBillGate, answerMark, answerNgoBaoChau, answerChiPu, answerCuTrongXoay, answerRonaldo;

    public static CallRelativeDialog newInStance(int trueCase) {
        CallRelativeDialog callRelativeDialog = new CallRelativeDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("TRUECASE", trueCase);
        callRelativeDialog.setArguments(bundle);
        return callRelativeDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_call_relative, null);
        builder.setView(view);
        ischeck = true;
        Bundle bundle = getArguments();
        trueCase = bundle.getInt("TRUECASE");
        initView(view);
        return builder.create();
    }

    private void initView(View view) {
        answerNgoBaoChau = trueCase;
        answerRonaldo = trueCase;
        answerCuTrongXoay = trueCase;
        answerChiPu = trueCase;
        answerMark = trueCase;
        answerBillGate = trueCase;
        tvAnswer = view.findViewById(R.id.tv_answer);
        ivBillGate = view.findViewById(R.id.iv_bill_gate);
        ivChipu = view.findViewById(R.id.iv_chi_pu);
        ivCuTrongXoay = view.findViewById(R.id.iv_cu_trong_xoay);
        ivMark = view.findViewById(R.id.iv_mark);
        ivNgoBaoChau = view.findViewById(R.id.iv_ngo_bao_chau);
        ivRonaldo = view.findViewById(R.id.iv_ronaldo);
        ivRonaldo.setOnClickListener(this);
        ivNgoBaoChau.setOnClickListener(this);
        ivMark.setOnClickListener(this);
        ivCuTrongXoay.setOnClickListener(this);
        ivChipu.setOnClickListener(this);
        ivBillGate.setOnClickListener(this);
        randomAnswer();
    }

    private void randomAnswer() {
        Random rd = new Random();
        int rd1 = rd.nextInt(3) + 1;
        while (rd1 == trueCase) {
            rd1 = rd.nextInt(3) + 1;
        }
        int rd2 = rd.nextInt(5) + 1;
        switch (rd2) {
            case 1:
                answerBillGate = rd1;
                break;
            case 2:
                answerMark = rd1;
                break;
            case 3:
                answerChiPu = rd1;
                break;
            case 4:
                answerCuTrongXoay = rd1;
                break;
            case 5:
                answerRonaldo = rd1;
                break;
            case 6:
                answerNgoBaoChau = rd1;
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        if (ischeck = false) {
            return;
        }
        ischeck = false;
        switch (v.getId()) {
            case R.id.iv_bill_gate:
                showAnswer(answerBillGate);
                break;
            case R.id.iv_cu_trong_xoay:
                showAnswer(answerCuTrongXoay);
                break;
            case R.id.iv_chi_pu:
                showAnswer(answerChiPu);
                break;
            case R.id.iv_mark:
                showAnswer(answerMark);
                break;
            case R.id.iv_ngo_bao_chau:
                showAnswer(answerNgoBaoChau);
                break;
            case R.id.iv_ronaldo:
                showAnswer(answerRonaldo);
                break;
            default:
                break;

        }
    }

    private void showAnswer(int n) {
        switch (n) {
            case 1:
                tvAnswer.setText("Đáp án của tôi là:A");
                break;
            case 2:
                tvAnswer.setText("Đáp án của tôi là:B");
                break;
            case 3:
                tvAnswer.setText("Đáp án của tôi là:C");
                break;
            case 4:
                tvAnswer.setText("Đáp án của tôi là:D");
                break;
            default:
                break;
        }
    }
}
