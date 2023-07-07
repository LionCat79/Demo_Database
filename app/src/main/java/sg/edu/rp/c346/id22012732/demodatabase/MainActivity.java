package sg.edu.rp.c346.id22012732.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    ArrayAdapter<String> adapter;
    ArrayList<String> taskList;
    ListView listView;
    private boolean isAscending = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        listView = findViewById(R.id.lv);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                EditText etTask = findViewById(R.id.etTask);
                EditText etDate = findViewById(R.id.etDate);

                String description = etTask.getText().toString();
                String date = etDate.getText().toString();

                db.insertTask(description, date);
                db.close();
            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<Task> data;
                if (isAscending) {
                    data = db.getTasksAscending();
                } else {
                    data = db.getTasksDescending();
                }
                db.close();

                taskList.clear();
                for (Task task : data) {
                    taskList.add(task.toString());
                    adapter.notifyDataSetChanged();
                }
            };
        });
    }
}