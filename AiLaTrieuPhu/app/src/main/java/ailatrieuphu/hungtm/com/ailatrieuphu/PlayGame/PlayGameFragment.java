package ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import ailatrieuphu.hungtm.com.ailatrieuphu.Database.Database;
import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class PlayGameFragment extends Fragment implements View.OnClickListener,LostDialog.ILostDialog {
    private Database database;
    private TextView tvCustomTiming;
    private CountDownTimer c;
    private SoundManager soundManager1, soundManager2, soundHelp;
    private List<Question> questions;
    private int level;
    private String levelMoneyLats,levelMoneyBeforeLast;
    private Button btnHelpAudience, btn5050, btnCall, btnReloadQuestion, btnStopGame;
    private Question question;
    private static Drawable drawableCreate[], drawableClick[], drawableTrue[], drawableFalse[];
    private TextView tv1, tv2, tv3, tv4, tvQuestion, tvLevel, tvMoney;
    private ImageView iv1, iv2, iv3, iv4, iv;
    private boolean checkA, checkB, checkC, checkD, check, isCheckHelp, isCheck50, isCheckReload,isCheckCall;
    private int trueCase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_game, container, false);
        database = new Database(container.getContext());
        Bundle bundle = getArguments();
        levelMoneyLats = bundle.getString("LEVEL_MONEY");
        soundManager1 = new SoundManager();
        soundManager1.initSound(getContext(), R.raw.background_music);
        soundManager1.loop();
        initsDrawableTransaction();
        setUpView(view);
        fillData(levelMoneyLats);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        btnHelpAudience.setOnClickListener(this);
        btn5050.setOnClickListener(this);
        btnStopGame.setOnClickListener(this);
        btnReloadQuestion.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        soundManager2 = new SoundManager();
        soundHelp = new SoundManager();
        return view;
    }

    private void setUpView(View view) {
        questions = database.select15Question();
        tv1 = view.findViewById(R.id.tv_1);
        tv2 = view.findViewById(R.id.tv_2);
        tv3 = view.findViewById(R.id.tv_3);
        tv4 = view.findViewById(R.id.tv_4);
        iv1 = view.findViewById(R.id.iv_1);
        iv2 = view.findViewById(R.id.iv_2);
        iv3 = view.findViewById(R.id.iv_3);
        iv4 = view.findViewById(R.id.iv_4);
        btnHelpAudience = view.findViewById(R.id.btn_support_help);
        btn5050 = view.findViewById(R.id.btn_support_5050);
        btnCall = view.findViewById(R.id.btn_support_call);
        tvLevel = view.findViewById(R.id.tv_level);
        tvMoney = view.findViewById(R.id.tv_money);
        btnStopGame = view.findViewById(R.id.btn_stop_game);
        btnReloadQuestion=view.findViewById(R.id.btn_reload);
        tvQuestion = view.findViewById(R.id.tv_title_question);
        tvCustomTiming = view.findViewById(R.id.customtiming);
        level = 1;
        levelMoneyBeforeLast="200.000";
        isCheckHelp = true;
        isCheck50 = true;
        isCheckReload=true;
        isCheckCall=true;    }

    private void setUpCheck() {
        iv = null;
        trueCase = 0;
        check = true;
        checkA = true;
        checkB = true;
        checkC = true;
        checkD = true;
        setDrawableImage(drawableCreate, iv1);
        setDrawableImage(drawableCreate, iv2);
        setDrawableImage(drawableCreate, iv3);
        setDrawableImage(drawableCreate, iv4);
    }

    private void initsDrawableTransaction() {
        Resources res = getResources();
        drawableCreate = new Drawable[2];
        drawableCreate[0] = res.getDrawable(R.drawable.level_list_image);
        drawableCreate[1] = res.getDrawable(R.drawable.level_list_image);
        drawableClick = new Drawable[2];
        drawableClick[0] = res.getDrawable(R.drawable.bg_choose1);
        drawableClick[1] = res.getDrawable(R.drawable.bg_choose2);
        drawableTrue = new Drawable[2];
        drawableTrue[0] = res.getDrawable(R.drawable.bg_true1);
        drawableTrue[1] = res.getDrawable(R.drawable.bg_true2);
        drawableFalse = new Drawable[2];
        drawableFalse[0] = res.getDrawable(R.drawable.bg_faile1);
        drawableFalse[1] = res.getDrawable(R.drawable.bg_faile2);
    }

    private void setDrawableImage(final Drawable[] drawable, final ImageView image) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(drawable);
        image.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);

    }

    public void fillData(String levelMoney) {
        soundManager1.start();
        question = questions.get(level - 1);
        tvQuestion.setText(question.getQuery());
        tv1.setText(questions.get(level - 1).getCaseA());
        tv2.setText(questions.get(level - 1).getCaseB());
        tv3.setText(questions.get(level - 1).getCaseC());
        tv4.setText(questions.get(level - 1).getCaseD());
        tvLevel.setText(questions.get(level - 1).getLevel() + "");
        tvMoney.setText(levelMoney);
        this.levelMoneyLats = levelMoney;
        setUpCheck();
        customeTiming();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_1:
                if (check && checkA) {
                    clickAnswer(iv1, R.raw.ans_a2, 1);
                }
                break;
            case R.id.tv_2:
                if (check && checkB) {
                    clickAnswer(iv2, R.raw.ans_b, 2);
                }
                break;
            case R.id.tv_3:
                if (check && checkC) {

                }
                clickAnswer(iv3, R.raw.ans_c, 3);
                break;
            case R.id.tv_4:
                if (check && checkD) {
                    clickAnswer(iv4, R.raw.ans_d2, 4);
                }
                break;
            case R.id.btn_support_help:
                if (check && isCheckHelp) {
                    helpAudience();
                }
                break;
            case R.id.btn_support_5050:
                if (check && isCheck50) {
                    soundHelp.initSound(getContext(), R.raw.sound5050);
                    soundHelp.start();
                    soundHelp.setOnComplete(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            support5050();
                        }
                    });
                }
                break;
            case R.id.btn_stop_game:
                openDialogLost(levelMoneyBeforeLast,level);
                break;
            case R.id.btn_reload:
                if (check&&isCheckReload) {
                    isCheckReload=false;
                    btnReloadQuestion.setBackgroundResource(R.drawable.false_restart);
                    c.cancel();
                    questions = database.select15Question();
                    fillData(levelMoneyLats);
                }
                break;
            case  R.id.btn_support_call:
                if(check&&isCheckCall){
                    isCheckCall=false;
                    btnCall.setBackgroundResource(R.drawable.false_call);
                    CallRelativeDialog callRelativeDialog=CallRelativeDialog.newInStance(question.getTrueCase());
                    callRelativeDialog.show(getFragmentManager(),CallRelativeDialog.class.getName());
                }
            default:
                break;

        }
        soundManager2.setOnComplete(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                systemHandling();
            }
        });

    }

    private void support5050() {
        Random rd = new Random();
        isCheck50 = false;
        btn5050.setBackgroundResource(R.drawable.false_percent50);
        int delete1 = rd.nextInt(3) + 1;
        while (delete1 == question.getTrueCase()) {
            delete1 = rd.nextInt(3) + 1;
        }
        deleteText(delete1);
        int delete2 = rd.nextInt(3) + 1;
        while (delete2 == question.getTrueCase() || delete2 == delete1) {
            delete2 = rd.nextInt(3) + 1;
        }
        deleteText(delete2);
    }

    private void deleteText(int index) {
        switch (index) {
            case 1:
                tv1.setText("");
                checkA = false;
                break;
            case 2:
                tv2.setText("");
                checkB = false;
                break;
            case 3:
                tv3.setText("");
                checkC = false;
                break;
            case 4:
                tv4.setText("");
                checkD = false;
                break;
            default:
                break;
        }
    }

    private void helpAudience() {
        isCheckHelp = false;
        btnHelpAudience.setBackgroundResource(R.drawable.false_hepl);
        HelpAudienceDialog helpAudienceDialog = HelpAudienceDialog.newInstance(checkA, checkB, checkC, checkD, question.getTrueCase(), level);
        helpAudienceDialog.show(getFragmentManager(), HelpAudienceDialog.class.getName());
    }

    private void clickAnswer(ImageView iv1, int idRaw, int trueCase1) {
        c.cancel();
        soundManager2.initSound(getContext(), idRaw);
        soundManager2.start();
        iv1.setImageResource(R.drawable.bg_choose1);
        check = false;
        iv = iv1;
        trueCase = trueCase1;
    }

    public void customeTiming() {
        c = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCustomTiming.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                tvCustomTiming.setText("0");
                moneyFinish();
            }
        };
        c.start();

    }

    public void systemHandling() {
        //xử lý sự kiện sau khi người chơi chọn đáp án
        if (trueCase == question.getTrueCase()) {
            level += 1;
            switch (question.getTrueCase()) {
                case 1:
                    showTrueCase(iv1, R.raw.true_a);
                    break;
                case 2:
                    showTrueCase(iv2, R.raw.true_b);
                    break;
                case 3:
                    showTrueCase(iv3, R.raw.true_c);
                    break;
                case 4:
                    showTrueCase(iv4, R.raw.true_d2);
                    break;
                default:
                    break;
            }
            if(level==15){
                showTrueCase(iv,R.raw.best_player);
                soundManager2.setOnComplete(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        openDialogLost(levelMoneyLats,level);
                    }
                });
                return;
            }
            soundManager2.setOnComplete(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    levelMoneyBeforeLast=levelMoneyLats;
                    ((MainActivity) getActivity()).openFragmentLevelMoney(level);
                    soundManager1.pause();
                }
            });
        } else {
            setDrawableImage(drawableFalse, iv);
            switch (question.getTrueCase()) {
                case 1:
                    showTrueCase(iv1, R.raw.lose_a);
                    break;
                case 2:
                    showTrueCase(iv2, R.raw.lose_b);
                    break;
                case 3:
                    showTrueCase(iv3, R.raw.lose_c);
                    break;
                case 4:
                    showTrueCase(iv4, R.raw.lose_d);
                    break;
                default:
                    break;
            }
            soundManager2.setOnComplete(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    moneyFinish();
                }
            });
        }
    }

    private void moneyFinish() {
        // dùng để  gán giá trị tiền thưởng của người chơi khi bị thua
        if (level < 5) {
            openDialogLost("200.000", level);
        } else if (level >= 5 && level < 10) {
            openDialogLost("2.000.000", level);
        } else if (level >= 10 && level < 15) {
            openDialogLost("22.000.000", level);
        }
    }

    private void showTrueCase(ImageView ivTrue, int idRaw) {
        //set background cho  đáp án đúng và phát nhạc  đáp án
        setDrawableImage(drawableTrue, ivTrue);
        soundManager2.initSound(getContext(), idRaw);
        soundManager2.start();
    }

    private void openDialogLost(String moneyLevel, int level) {
        LostDialog lostDialog = LostDialog.newInstance(moneyLevel, level);
        lostDialog.setILostDialog(this);
        lostDialog.show(getFragmentManager(), LostDialog.class.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundManager2.remove();
        soundManager1.remove();
        soundHelp.remove();
    }

    @Override
    public void saveScore(String name, int score, int level, String money) {
      long check=database.saveScore(name,score,level,money);
    }
}

