package answerofgod.com.transcoord;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    Spinner fromCoord,toCoord,format;
    Button getCoord;
    static TextView resultText;
    EditText xGrid,yGrid;
    String coord[]={"TM","KTM","UTM","CONGNAMUL","WGS84","BESSEL","WTM","WKTM","WUTM","WCONGNAMUL"};

    ArrayAdapter<String> fromArray,toArray;
    String from,to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        fromCoord=(Spinner)findViewById(R.id.fromCoord);
        toCoord=(Spinner)findViewById(R.id.toCoord);
        getCoord=(Button)findViewById(R.id.getCoord);
        resultText=(TextView)findViewById(R.id.resultText);
        xGrid=(EditText)findViewById(R.id.xGrid);
        yGrid=(EditText)findViewById(R.id.yGrid);
        fromArray=new ArrayAdapter(getApplication(),R.layout.spinner_text,coord);
        fromCoord.setAdapter(fromArray);
        toArray=new ArrayAdapter(getApplication(),R.layout.spinner_text,coord);
        toCoord.setAdapter(toArray);

        getCoord.setOnClickListener(this);
        fromCoord.setOnItemSelectedListener(this);
        toCoord.setOnItemSelectedListener(this);


    }
    void getThread(){
        String x,y;
        if(xGrid!=null&&yGrid!=null){
            x=xGrid.getText().toString();
            y=yGrid.getText().toString();
            GetTransCoordThread.active=true;
            GetTransCoordThread getCoordthread=new GetTransCoordThread(false,x,y,from,to);		//스레드생성(UI 스레드사용시 system 뻗는다)
            getCoordthread.start();	//스레드 시작
        }else{
            Toast.makeText(getApplication(),"좌표값이 모두 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
        }

    }
    public static void  TransCoordThreadResponse(String x,String y) {    //대기정보 가져온 결과값
        if(x.equals("NaN")||y.equals("NaN")){
            resultText.setText("좌표값이 잘못 입력되었거나 해당값이 없습니다.");
        }else{

            resultText.setText("변환된 좌표값은 "+x+","+y+"입니다.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.getCoord:

                getThread();
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){

            case R.id.fromCoord:
                from=coord[i];
                break;
            case R.id.toCoord:
                to=coord[i];
                break;



            default:

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
