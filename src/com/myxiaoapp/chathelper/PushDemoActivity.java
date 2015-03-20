package com.myxiaoapp.chathelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.android.R;
import com.myxiaoapp.android.SelectPicGroupActivity;
import com.myxiaoapp.utils.Utils;
import com.myxiaoapp.view.CollapsibleTextView;

public class PushDemoActivity extends ActionBarActivity {

	private static final String USER_ID = "589396412218359559";
	private static final String CHANNEL_ID = "4042017386297853232";

	private CollapsibleTextView collText;

	private String text = "人民网北京11月15日电 11月14日，李克强对缅甸进行正式访问。"
			+ "在所有到缅甸参加东亚合作峰会的外方领导人中，克强总理是缅方唯一正式接待的外国领导人。不到一"
			+ "天的访问时间里，总理都参加了什么活动，说了啥？镜鉴为你独家报道。28年前，当时在团中央工作的"
			+ "强哥，曾率中国青年代表团受邀访问缅甸，去了仰光、曼德勒和眉苗。当时缅甸给他的印象是，“民风淳，"
			+ "风光好”。时隔28年，强哥担任总理后首次对缅甸进行正式访问。14日，克强总理见了缅甸总统吴登盛，以"
			+ "及议长吴瑞曼。总理和他们谈了啥？当然是中缅合作，“胞波”情谊。“胞波”啥意思？就是缅语中“兄弟”的发"
			+ "音，是对中国人的亲切称呼。和中国有类似称呼的国家并不多，比如我们习惯把巴基斯坦称为“巴铁”，还"
			+ "有“中泰一家亲”等，总之，都是感情好到一定份儿上才行。访问不到一天，尽管如此，总理在原定计划之"
			+ "外特意增加了一场活动，去内比都第十四中学同学生交流。不要小瞧了这场活动，其中大有深意。先卖"
			+ "个关子。中缅“胞波”情有年头。缅甸在不同社会制度国家中第一个承认新中国，在亚洲国家中第一个与"
			+ "中国签订友好和互不侵犯条约，中缅都是和平共处五项原则倡议发起国。当年，周恩来、陈毅等老一辈领"
			+ "导人都访问过缅甸。周总理曾九次访缅，还和时任缅总理吴巴瑞在中缅边境亲手种下了两棵象征中缅友谊长存"
			+ "的缅桂花树，如今已经是枝繁叶茂！说完历史，说现实。"
			+ "中国国力增强，缅甸的政治形态也发生变化，两国是亲上加亲，还是渐行渐远？事实胜于雄辩。";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_demo);
		collText = (CollapsibleTextView) findViewById(R.id.ctv);
		collText.setText(text);
	}

}
