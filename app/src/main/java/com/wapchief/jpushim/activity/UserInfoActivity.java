package com.wapchief.jpushim.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wapchief.jpushim.R;
import com.wapchief.jpushim.framework.base.BaseActivity;
import com.wapchief.jpushim.framework.helper.SharedPrefHelper;
import com.wapchief.jpushim.framework.utils.StringUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wapchief on 2017/7/28.
 * 用户资料
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.userinfo_scroll)
    ScrollView mUserinfoScroll;
    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.title_bar_left)
    LinearLayout mTitleBarLeft;
    @BindView(R.id.title_options_tv)
    TextView mTitleOptionsTv;
    @BindView(R.id.title_options_img)
    ImageView mTitleOptionsImg;
    @BindView(R.id.title_bar_right)
    RelativeLayout mTitleBarRight;
    @BindView(R.id.bottom_bar_tv1)
    TextView mBottomBarTv1;
    @BindView(R.id.bottom_bar_left)
    RelativeLayout mBottomBarLeft;
    @BindView(R.id.bottom_bar_tv2)
    TextView mBottomBarTv2;
    @BindView(R.id.bottom_bar_right)
    RelativeLayout mBottomBarRight;
    @BindView(R.id.title)
    LinearLayout mTitle;
    @BindView(R.id.userinfo_avatar)
    CircleImageView mUserinfoAvatar;
    @BindView(R.id.userinfo_nikename)
    TextView mUserinfoNikename;
    @BindView(R.id.userinfo_signature)
    TextView mUserinfoSignature;
    @BindView(R.id.userinfo_username)
    TextView mUserinfoUsername;
    @BindView(R.id.userinfo_gender)
    TextView mUserinfoGender;
    @BindView(R.id.userinfo_birthday)
    TextView mUserinfoBirthday;
    @BindView(R.id.userinfo_region)
    TextView mUserinfoRegion;
    @BindView(R.id.userinfo_mtime)
    TextView mUserinfoMtime;
    @BindView(R.id.userinfo_fill)
    ImageView mUserinfoFill;
    private String userName;
    private SharedPrefHelper helper;
    private String avtar = "";
    private String getUserName="";
    @Override
    protected int setContentView() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initView() {
        getUserName = getIntent().getStringExtra("USERNAME");
        initBar();
        initUserInfo(getUserName);
        initScroll();
    }

    /*获取用户资料*/
    private void initUserInfo(String userName) {
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                Log.e("iiiiiiiii", ":" + userInfo);
                if (i == 0) {
                    avtar = userInfo.getAvatar();
                    mUserinfoBirthday.setText(userInfo.getBirthday() + "");
                    mUserinfoGender.setText(StringUtils.constant2String(userInfo.getGender()+""));
                    mUserinfoMtime.setText(userInfo.getmTime() + "");
                    mUserinfoNikename.setText(userInfo.getNickname() + "");
                    mUserinfoUsername.setText(userInfo.getUserName() + "");
                    if(userInfo.getSignature().equals("")){
                        mUserinfoSignature.setText("签名：说点儿什么吧～");
                    }else {
                        mUserinfoSignature.setText("签名：" + userInfo.getSignature());
                    }
                    mUserinfoRegion.setText(userInfo.getRegion() + "");
                } else {
                }
            }
        });

        if (avtar == null || avtar.equals("")) {
            mUserinfoAvatar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_user));
        } else {
            Picasso.with(UserInfoActivity.this)
                    .load(avtar)
                    .into(mUserinfoAvatar);
        }
    }

    /*滚动事件*/
    @TargetApi(Build.VERSION_CODES.M)
    private void initScroll() {
        mUserinfoScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                Log.e("scroll===", i + " ," + i1 + " ," + i2 + " ," + i3);
                if (i1 > 460) {
                    mTitleBarLeft.setBackground(getResources().getDrawable(R.drawable.shape_titlebar));
                    mTitleBarRight.setBackground(getResources().getDrawable(R.drawable.shape_titlebar2));
                    mTitleBarTitle.setText("个人资料");
                } else {
                    mTitleBarLeft.setBackground(getResources().getDrawable(R.color.color_transparent));
                    mTitleBarRight.setBackground(getResources().getDrawable(R.color.color_transparent));
                    mTitleBarTitle.setText("");
                }
            }
        });
    }

    private void initBar() {
        helper = SharedPrefHelper.getInstance();
        mTitleBarLeft.setBackgroundColor(Color.parseColor("#00000000"));
        mTitleBarRight.setBackgroundColor(Color.parseColor("#00000000"));
        mTitleBarBack.setImageDrawable(getResources().getDrawable(R.mipmap.icon_back));
        mTitleOptionsTv.setText("更多");
        mTitleOptionsTv.setVisibility(View.VISIBLE);
        mTitleBarTitle.setText("");
        mBottomBarLeft.setVisibility(View.GONE);
        userName = getIntent().getStringExtra("USERNAME");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_bar_back, R.id.title_options_tv, R.id.bottom_bar_tv1, R.id.bottom_bar_tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_options_tv:
                break;
            case R.id.bottom_bar_tv2:
                /*创建会话*/
                Conversation.createSingleConversation(getIntent().getStringExtra("USERNAME"), "");
                Intent intent = new Intent(UserInfoActivity.this, ChatMsgActivity.class);
                intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(intent);
//                JMessageClient.c


                break;
        }
    }
}