package edu.cuhk.csci3310_finaciallogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import edu.cuhk.csci3310_finaciallogger.game.PresetDataReader;
import edu.cuhk.csci3310_finaciallogger.game.GameView;

public class GameActivity extends AppCompatActivity {
    private Boolean m_FirstTime;
    private HashMap<String, ArrayList<String>> m_Data;
    private PresetDataReader m_Reader;
    private static String PRESET_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private static String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";

    private static final int GAME_TRANSACTION_ACTIVITY = 1;

    private FrameLayout m_FrameLayout;
    private GameView m_GameView;
    private RelativeLayout m_GameOverlay;

    long m_TimeLastPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting up m_Data
        m_Reader = PresetDataReader.getInstance();
        m_Reader.loadData(this, getResources());
        m_Data = m_Reader.getData();

        /*
        m_Data = new HashMap<>();
        m_Data.put("preset_list", new LinkedList<>());
        m_Data.put("preset_list_amount", new LinkedList<>());
        m_Data.put("preset_list_category", new LinkedList<>());
        m_Data.put("record_item", new LinkedList<>());
        m_Data.put("record_amount", new LinkedList<>());
        m_Data.put("record_date", new LinkedList<>());
        m_Data.put("record_category", new LinkedList<>());

        //Loading data
        File file = new File(PRESET_FILE_PATH);
        InputStream isd = null;
        InputStream isdr = null;

        if (file.exists()) {
            try {
                m_FirstTime = false;
                isd = new FileInputStream(PRESET_FILE_PATH);
                isdr= new FileInputStream(RECORD_FILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            m_FirstTime = true;
            isd = getResources().openRawResource(R.raw.preset);
            isdr = getResources().openRawResource(R.raw.record);
        }

        //Reading from input stream for creating the preset list in the Logging Activity
        BufferedReader br = new BufferedReader(new InputStreamReader(isd, Charset.forName("UTF-8")));
        BufferedReader brr = new BufferedReader(new InputStreamReader(isdr, Charset.forName("UTF-8")));
        //for creating file to save info for first time open the app
        FileOutputStream fos = null;
        FileOutputStream fosd = null;
        FileOutputStream fosr = null;
        FileOutputStream fosrd = null;
        String linewrite = "";
        String linewriter = "";

        //creating files for preset options
        try {
            if (m_FirstTime) {
                fos = openFileOutput("preset", MODE_PRIVATE);
                fosd = openFileOutput("preset_dup", MODE_PRIVATE);
            }
            while (((linewrite = br.readLine()) != null)) {
                String output = linewrite + "\n";
                String[] store = linewrite.split(",");
                if (m_FirstTime) {
                    fos.write(output.getBytes());
                    fosd.write(output.getBytes());
                }

                m_Data.get("preset_list").addLast(store[0]);
                m_Data.get("preset_list_amount").addLast(store[1]);
                m_Data.get("preset_list_category").addLast(store[2]);
            }
            if (m_FirstTime) {
                fos.close();
                fosd.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating local saving for logging record
        try {
            if (m_FirstTime) {
                fosr = openFileOutput("record", MODE_PRIVATE);
                fosrd = openFileOutput("record_dup", MODE_PRIVATE);
            }
            while (((linewriter = brr.readLine()) != null)) {
                String output = linewriter + "\n";
                String[] store = linewriter.split(",");
                if (m_FirstTime) {
                    fosr.write(output.getBytes());
                    fosrd.write(output.getBytes());
                }
                m_Data.get("record_item").addLast(store[0]);
                m_Data.get("record_amount").addLast(store[1]);
                m_Data.get("record_date").addLast(store[2]);
                m_Data.get("record_category").addLast(store[3]);
            }
            br.close();
            brr.close();
            isd.close();
            isdr.close();
            if (m_FirstTime) {
                fosr.close();
                fosrd.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        */

        //Preparing the frame layout
        m_FrameLayout = new FrameLayout(this);

        //Setting up the game overlay
        m_GameOverlay = new RelativeLayout(this);

        //Setting up the overlay
        //Setting up variables
        View anchorView;
        Button button;
        RelativeLayout.LayoutParams rl;
        LinearLayout.LayoutParams ll;

        //Not gonna lie, should've used XML + layout inflater
        //I am regretting it
        /*
         * LEFT AND RIGHT BUTTONS
         */
        //Setting up the first anchor view
        anchorView = new View(this);
        int anchor_view_1_id = View.generateViewId();
        anchorView.setId(anchor_view_1_id);
        rl = new RelativeLayout.LayoutParams(0, 200);
        rl.addRule(RelativeLayout.CENTER_VERTICAL);
        anchorView.setLayoutParams(rl);
        m_GameOverlay.addView(anchorView);

        //Setting up the left button
        Button leftButton = new Button(this);
        leftButton.setBackgroundResource(R.drawable.left_button);
        int left_button_id = View.generateViewId();
        leftButton.setId(left_button_id);
        rl = new RelativeLayout.LayoutParams((int) (90 * 1.5f), (int) (90 * 1.5f));
        rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl.addRule(RelativeLayout.ABOVE, anchor_view_1_id);
        rl.setMargins(16, 16, 16, 16);
        leftButton.setMinHeight(0);
        leftButton.setMinimumHeight(0);
        leftButton.setMinWidth(0);
        leftButton.setMinimumWidth(0);
        leftButton.setLayoutParams(rl);
        m_GameOverlay.addView(leftButton);

        //Setting up the right button
        Button rightButton = new Button(this);
        rightButton.setBackgroundResource(R.drawable.right_button);
        int right_button_id = View.generateViewId();
        rightButton.setId(right_button_id);
        rl = new RelativeLayout.LayoutParams((int) (90 * 1.5f), (int) (90 * 1.5f));
        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addRule(RelativeLayout.ABOVE, anchor_view_1_id);
        rl.setMargins(16, 16, 16, 16);
        rightButton.setMinHeight(0);
        rightButton.setMinimumHeight(0);
        rightButton.setMinWidth(0);
        rightButton.setMinimumWidth(0);
        rightButton.setLayoutParams(rl);

        m_GameOverlay.addView(rightButton);

        /*
         * CURRENCY INFO BAR
         */
        //Setting up the currency info bar
        View currencyInfoBar = View.inflate(this, R.layout.currency_info_bar, null);
        int currency_info_bar_id = View.generateViewId();
        currencyInfoBar.setId(currency_info_bar_id);
        rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        currencyInfoBar.setLayoutParams(rl);
        m_GameOverlay.addView(currencyInfoBar);

        /*
         * ANIMAL INFO BAR
         */
        //Setting up the animal info bar
        View animalInfoBar = View.inflate(this, R.layout.animal_info_bar, null);
        int animal_info_bar_id = View.generateViewId();
        animalInfoBar.setId(animal_info_bar_id);
        rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ABOVE, currency_info_bar_id);
        animalInfoBar.setLayoutParams(rl);
        m_GameOverlay.addView(animalInfoBar);

        /*
         * TOTAL CPM INFO
         */
        //Setting up the total cpm info
        View totalCPMInfo = View.inflate(this, R.layout.total_cpm_info_bar, null);
        rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.setMargins(16, 16, 16, 16);
        totalCPMInfo.setLayoutParams(rl);
        m_GameOverlay.addView(totalCPMInfo);

        /*
         * OVERVIEW AND LOG BUTTON
         */
        //Setting up the second anchor view
        anchorView = new View(this);
        int anchor_view_2_id = View.generateViewId();
        anchorView.setId(anchor_view_2_id);
        rl = new RelativeLayout.LayoutParams(0, 200);
        rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
        anchorView.setLayoutParams(rl);
        m_GameOverlay.addView(anchorView);

        //Setting up the overview button
        button = new Button(this);
        button.setBackgroundResource(R.drawable.overview_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, OverviewActivity.class);
                intent.putExtra("recordItem", m_Data.get("record_item"));
                intent.putExtra("recordAmount", m_Data.get("record_amount"));
                intent.putExtra("recordCategory", m_Data.get("record_category"));
                intent.putExtra("recordDate", m_Data.get("record_date"));
                startActivity(intent);
            }
        });
        int overview_button_id = View.generateViewId();
        button.setId(overview_button_id);
        rl = new RelativeLayout.LayoutParams((int) (290 * 1.5f), (int) (160 * 1.5f));
        rl.addRule(RelativeLayout.ABOVE, animal_info_bar_id);
        rl.addRule(RelativeLayout.LEFT_OF, anchor_view_2_id);
        rl.setMargins(32, 32, 32, 32);
        button.setLayoutParams(rl);
        m_GameOverlay.addView(button);

        //Setting up the log button
        button = new Button(this);
        button.setBackgroundResource(R.drawable.log_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, LoggingActivity.class);
                intent.putExtra("presetItem", m_Data.get("preset_list"));
                intent.putExtra("presetItemAmount", m_Data.get("preset_list_amount"));
                intent.putExtra("presetItemCategory", m_Data.get("preset_list_category"));
                startActivity(intent);
            }
        });
        int log_button_id = View.generateViewId();
        button.setId(log_button_id);
        rl = new RelativeLayout.LayoutParams((int) (290 * 1.5f), (int) (160 * 1.5f));
        rl.addRule(RelativeLayout.ABOVE, animal_info_bar_id);
        rl.addRule(RelativeLayout.RIGHT_OF, anchor_view_2_id);
        rl.setMargins(32, 32, 32, 32);
        button.setLayoutParams(rl);
        m_GameOverlay.addView(button);

        /*
         * SPINNING WHEEL BUTTON
         */
        //Setting up the spinning wheel button
        button = new Button(this);
        button.setBackgroundResource(R.drawable.wheel_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //m_NewAnimals.
                Intent intent = new Intent(GameActivity.this, GameTransactionActivity.class);
                startActivityForResult(intent, GAME_TRANSACTION_ACTIVITY);
            }
        });
        int spinning_wheel_button_id = View.generateViewId();
        button.setId(spinning_wheel_button_id);
        rl = new RelativeLayout.LayoutParams((int) (90 * 1.5f), (int) (90 * 1.5f));
        rl.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.setMargins(16, 16, 16, 16);
        rightButton.setMinHeight(0);
        rightButton.setMinimumHeight(0);
        rightButton.setMinWidth(0);
        rightButton.setMinimumWidth(0);
        button.setLayoutParams(rl);
        m_GameOverlay.addView(button);

        //Setting up the game view
        Rect bounds = getWindowManager().getCurrentWindowMetrics().getBounds();
        TypedValue tv = new TypedValue();

        int statusBarHeight = 0;
        int resource = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resource > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resource);
        }
        int navigationBarHeight = 0;
        resource = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if(resource > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resource);
        }
        int actionBarHeight = 0;
        if (this.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        m_GameView = new GameView(
                this,
                bounds.width(),
                bounds.height() - actionBarHeight - statusBarHeight - navigationBarHeight,
                leftButton, rightButton,
                currencyInfoBar.findViewById(R.id.bucks_text_view_game),
                currencyInfoBar.findViewById(R.id.coins_text_view_game),
                animalInfoBar.findViewById(R.id.animal_name_info_bar),
                animalInfoBar.findViewById(R.id.coins_per_minute_info_bar),
                totalCPMInfo.findViewById(R.id.total_cpm_total_cpm_info_bar));

        //Setting up the frame layout
        m_FrameLayout.addView(m_GameView);
        m_FrameLayout.addView(m_GameOverlay);

        setContentView(m_FrameLayout);

        m_TimeLastPaused = System.nanoTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_GameView.pause();
        m_TimeLastPaused = System.nanoTime();
        Log.d("GameActivity", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_GameView.resume(m_TimeLastPaused);
        Log.d("GameActivity", "onResume");

        /*
        // read the record list again
        m_Data.put("record_item", new LinkedList<>());
        m_Data.put("record_amount", new LinkedList<>());
        m_Data.put("record_date", new LinkedList<>());
        m_Data.put("record_category", new LinkedList<>());
        InputStream isdr = null;
        try {
            isdr = new FileInputStream(RECORD_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Reading from input stream for creating the preset list in the Logging Activity
        BufferedReader brr = new BufferedReader(new InputStreamReader(isdr, StandardCharsets.UTF_8));

        String linewriter = "";
        try {
            while (((linewriter = brr.readLine()) != null)) {
                String output = linewriter + "\n";
                String[] store = linewriter.split(",");
                m_Data.get("record_item").addLast(store[0]);
                m_Data.get("record_amount").addLast(store[1]);
                m_Data.get("record_date").addLast(store[2]);
                m_Data.get("record_category").addLast(store[3]);
            }
            brr.close();
            isdr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
        m_Reader.loadData(this, getResources());
        m_Data = m_Reader.getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GAME_TRANSACTION_ACTIVITY) {
            m_GameView.updateGameStates();
        }
    }

    public void openRecord(View view) {
        Intent intent = new Intent(GameActivity.this, OverviewActivity.class);
        intent.putExtra("recordItem", m_Data.get("record_item"));
        intent.putExtra("recordAmount", m_Data.get("record_amount"));
        intent.putExtra("recordCategory", m_Data.get("record_category"));
        intent.putExtra("recordDate", m_Data.get("record_date"));
        startActivity(intent);
    }
}