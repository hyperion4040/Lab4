package zpam.lab4.lab4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import zpam.lab4.lab4.model.Author;
import zpam.lab4.lab4.model.Opinion;
import zpam.lab4.lab4.model.Rate;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText firstName;
    private EditText lastName;
    private EditText note;
    private RatingBar ratingBar;
    private Button button;
    private RequestQueue queue;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ekran();

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      Opinion  opinion = new Opinion(
                                new Author(firstName.getText().toString(),lastName.getText().toString()),
                                new Rate(ratingBar.getNumStars(),note.getText().toString()
                                )

                        );
                        requestPost(opinion);




                    }
                }
        );



        setContentView(linearLayout);


    }

    private void ekran(){
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(linearLayout.VERTICAL);
        firstName = new EditText(this);
        firstName.setHint("first Name");
        lastName = new EditText(this);
        lastName.setHint("Last Name");
        note = new EditText(this);
        note.setHint("Note");
        ratingBar = new RatingBar(this);
        button = new Button(this);
        button.setText("wyślij");

        linearLayout.addView(firstName);
        linearLayout.addView(lastName);
        linearLayout.addView(note);
        linearLayout.addView(ratingBar);
        linearLayout.addView(button);
    }

    public void requestPost(Opinion opinion){
        final String opinionJson = new Gson().toJson(opinion);

        url = "http://10.253.8.176:8080/opinion/save";
        queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//tu należy samodzielnie zaimplementować wyświetlanie response
                        Toast.makeText(getApplicationContext(),
                                response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//tu należy samodzielnie zaimplementować wyświetlanie errora
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return opinionJson == null ? null :
                            opinionJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        queue.add(postRequest);
        queue.start();



    }
}
