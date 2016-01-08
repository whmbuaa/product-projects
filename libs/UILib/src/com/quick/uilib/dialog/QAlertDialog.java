/**
 * 
 */
package com.quick.uilib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quick.uilib.R;
import com.quick.uilib.toast.ToastUtil;





/**
 * @author wanghaiming
 *
 */
public class QAlertDialog extends Dialog {

	public static final String KEY_DIALOG_OUTPUT = "key_dialog_output";
	
	public enum ButtonType {
		POSITIVE,
		NEGATIVE
	}
	private View   mTitleView;
	private TextView mTvTitle;
	private String mStrTitle;
	
	private LinearLayout     mContentViewContainer;
	private TextView mTvMessage;
	private ImageView mContentImage;
	private String mStrMessage;
	private int    mImageResId = -1;
	
	private View   mButtonContainer;
	private TextView mButtonNegative;
	private String mStrButtonNegative;
	private TextView mButtonPositive;
	private String mStrButtonPositive;
	
	private DialogButtonOnClickListener   mOnClickListener;
	
	public interface DialogButtonOnClickListener {
        public void onPositiveClicked(Dialog dialog,Bundle result);
        public void onNegativeClicked(Dialog dialog);
    }
	public QAlertDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}



	protected QAlertDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public QAlertDialog(Context context ) {
		super(context,R.style.q_alert_dialog);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View mainView = View.inflate(getContext(), R.layout.alert_dialog, null);
		init(mainView);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		this.setContentView(mainView, params);
	}

	protected void init(View mainView){
	
		initDialogAppearance(mainView);
	
		mTitleView = mainView.findViewById(R.id.dialog_title_container);
		mTvTitle = (TextView)mainView.findViewById(R.id.dialog_title);
		mContentViewContainer = (LinearLayout)mainView.findViewById(R.id.dialog_content_container);
		mTvMessage = (TextView)mainView.findViewById(R.id.dialog_message);
		mContentImage = (ImageView)mainView.findViewById(R.id.content_image);
		mButtonContainer = mainView.findViewById(R.id.dialog_button_container);
		mButtonNegative = (TextView)mainView.findViewById(R.id.dialog_negative_button);
		mButtonPositive = (TextView)mainView.findViewById(R.id.dialog_positive_button);
		// init title
		if(mStrTitle != null){
			mTitleView.setVisibility(View.VISIBLE);
			mTvTitle.setText(mStrTitle);
		}
		else{
			mTitleView.setVisibility(View.GONE);
		}
		
		//init message
		if(mStrMessage != null){
			mTvMessage.setVisibility(View.VISIBLE);
			mTvMessage.setText(mStrMessage);
		}
		else {
			mTvMessage.setVisibility(View.GONE);
			//set content if no message
			View contentView = getDialogContentView();
			if(contentView != null){
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				contentView.setLayoutParams(params);
				mContentViewContainer.addView(contentView);
			}
		}
		
		//init image content
		if(mImageResId != -1 ){
			mContentImage.setVisibility(View.VISIBLE);
			mContentImage.setImageResource(mImageResId);
		}
		else {
			mContentImage.setVisibility(View.GONE);
		}
		
		// init buttons
		if((mStrButtonNegative == null)&&(mStrButtonPositive == null)){
			mButtonContainer.setVisibility(View.GONE);
		}
		else {
			mButtonContainer.setVisibility(View.VISIBLE);
			
			
			Drawable singleBgDrawable = generateShapeDrawable(getContext(), DIALOG_BUTTON_COLOR, 0, 0, DIALOG_EDGE_RADIUS, DIALOG_EDGE_RADIUS);
			Drawable singleBgDrawablePressed = generateShapeDrawable(getContext(), DIALOG_BUTTON_COLOR_PRESSED, 0, 0, DIALOG_EDGE_RADIUS, DIALOG_EDGE_RADIUS);
			StateListDrawable singleButtonBg = new StateListDrawable();
			singleButtonBg.addState(new int[]{android.R.attr.state_pressed}, singleBgDrawablePressed);
			singleButtonBg.addState(new int[]{}, singleBgDrawable);
			
			if(mStrButtonNegative != null){
				mButtonNegative.setVisibility(View.VISIBLE);
				mButtonNegative.setText(mStrButtonNegative);
				mButtonNegative.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(mOnClickListener != null){
							mOnClickListener.onNegativeClicked(QAlertDialog.this);
						}
					}
				});
			}
			else {
			
				mButtonPositive.setBackgroundDrawable(singleButtonBg);
			
			}
			
			
			if(mStrButtonPositive != null){
				mButtonPositive.setVisibility(View.VISIBLE);
				mButtonPositive.setText(mStrButtonPositive);
				mButtonPositive.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if((checkInputData())&&(mOnClickListener != null)){
							mOnClickListener.onPositiveClicked(QAlertDialog.this,getResultData());
						}
					}
				});
			}
			else{
				mButtonNegative.setBackgroundDrawable(singleButtonBg);
			}
		}
	}
	
	
	public QAlertDialog setTitle(String title){
		mStrTitle = title;
		return this;
	}
	public QAlertDialog setMessage(String message){
		mStrMessage = message;
		return this;
	}
	public QAlertDialog setContentImageRes(int imageResId){
		mImageResId = imageResId;
		return this;
	}
	public QAlertDialog setButtonNegative(String btnStr){
		mStrButtonNegative = btnStr;
		return this;
	}
	public QAlertDialog setButtonPositive(String btnStr){
		mStrButtonPositive = btnStr;
		return this;
	}
	public QAlertDialog setOnClickListener(DialogButtonOnClickListener listener){
		mOnClickListener = listener;
		return this;
	}
	
	protected void setBtnEnabled(ButtonType type, boolean enabled){
		switch(type){
		case POSITIVE :
			mButtonPositive.setEnabled(enabled);
			break;
		case NEGATIVE :
			mButtonNegative.setEnabled(enabled);
			break;
		}
	}
	
	//customize the appearance of dialog including backgroud, linewidth, line color ,radius etc
	
	
	private static final int DIALOG_BG = 0xfffe5919;
	private static final int DIALOG_BUTTON_COLOR = 0xfffe5919;
	private static final int DIALOG_BUTTON_COLOR_PRESSED = 0xfff6512b;
	private static final int DIALOG_LINE_COLOR = 0xffeb4d29;
	private static final int DIALOG_LINE_WIDTH = 1 ; //DP
	private static final int DIALOG_EDGE_RADIUS = 5 ; //DP
	
	
	public static Drawable generateShapeDrawable(Context context,int color, int leftTopRadius,int rightTopRadius, int rightBottomRadius, int leftBottomRadius){
		
		leftTopRadius = dpToPx(context, leftTopRadius);
		rightTopRadius = dpToPx(context, rightTopRadius);
		leftBottomRadius = dpToPx(context, leftBottomRadius);
		rightBottomRadius = dpToPx(context, rightBottomRadius);
		
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setColor(color);
		drawable.setCornerRadii(new float[]{leftTopRadius,leftTopRadius,rightTopRadius,rightTopRadius,rightBottomRadius,rightBottomRadius,leftBottomRadius,leftBottomRadius});
		return drawable;
	
	}
	
	private void initDialogAppearance(View dialogView){
		// set background

		Drawable dialogBackground = generateShapeDrawable(getContext(), DIALOG_BG, DIALOG_EDGE_RADIUS, DIALOG_EDGE_RADIUS, DIALOG_EDGE_RADIUS, DIALOG_EDGE_RADIUS);
		dialogView.setBackgroundDrawable(dialogBackground);
		
		//divider line below title
		View lineBelowTitle = dialogView.findViewById(R.id.line_below_title);
		lineBelowTitle.setBackgroundColor(DIALOG_LINE_COLOR);
		LayoutParams params = lineBelowTitle.getLayoutParams();
		params.height = dpToPx(getContext(), DIALOG_LINE_WIDTH);
		lineBelowTitle.setLayoutParams(params);
		
		// button containers
		View buttonsContainer = dialogView.findViewById(R.id.dialog_button_container);
		buttonsContainer.setPadding(0, dpToPx(getContext(), DIALOG_LINE_WIDTH), 0, 0);
		Drawable containerBackground = generateShapeDrawable(getContext(), DIALOG_LINE_COLOR,0 , 0, DIALOG_EDGE_RADIUS, DIALOG_EDGE_RADIUS);
		buttonsContainer.setBackgroundDrawable(containerBackground);
		
		
		// button divider
		View buttonsDivider = dialogView.findViewById(R.id.button_divider);
		params = buttonsDivider.getLayoutParams();
		params.width = dpToPx(getContext(), DIALOG_LINE_WIDTH);
		buttonsDivider.setLayoutParams(params);
		
		
		// set button background
		Drawable leftButtonBgDrawable = generateShapeDrawable(getContext(), DIALOG_BUTTON_COLOR, 0, 0, 0, DIALOG_EDGE_RADIUS);
		Drawable leftButtonBgDrawablePressed = generateShapeDrawable(getContext(), DIALOG_BUTTON_COLOR_PRESSED, 0, 0, 0, DIALOG_EDGE_RADIUS);
		StateListDrawable leftButtonBg = new StateListDrawable();
		leftButtonBg.addState(new int[]{android.R.attr.state_pressed}, leftButtonBgDrawablePressed);
		leftButtonBg.addState(new int[]{}, leftButtonBgDrawable);
		dialogView.findViewById(R.id.dialog_negative_button).setBackgroundDrawable(leftButtonBg);
		
		
		Drawable rightButtonBgDrawable = generateShapeDrawable(getContext(), DIALOG_BUTTON_COLOR, 0, 0,  DIALOG_EDGE_RADIUS,0);
		Drawable rightButtonBgDrawablePressed = generateShapeDrawable(getContext(), DIALOG_BUTTON_COLOR_PRESSED, 0, 0,  DIALOG_EDGE_RADIUS,0);
		StateListDrawable rightButtonBg = new StateListDrawable();
		rightButtonBg.addState(new int[]{android.R.attr.state_pressed}, rightButtonBgDrawablePressed);
		rightButtonBg.addState(new int[]{}, rightButtonBgDrawable);
		dialogView.findViewById(R.id.dialog_positive_button).setBackgroundDrawable(rightButtonBg);
		

		
	}
	
	private static int dpToPx(Context context,int dp){
		return (int)(context.getResources().getDisplayMetrics().density*dp);
	}
	
	// child class to customize
	protected View getDialogContentView(){
		return null;
	}
	protected  Bundle getResultData(){ return null; }
	protected boolean checkInputData(){ return true; }
	
	
	public static void test(final Context context){
		QAlertDialog dialog = new QAlertDialog(context)
		.setTitle("王宝强")
//		.setMessage("我是金三顺，大家好，澡堂老板家的男人")
		.setContentImageRes(R.drawable.ic_launcher)
		.setButtonNegative("取消 ")
		.setButtonPositive("确定")
		.setOnClickListener(new DialogButtonOnClickListener() {
			
			@Override
			public void onPositiveClicked(Dialog dialog, Bundle result) {
				// TODO Auto-generated method stub
				ToastUtil.showToast(context, "点击确定");
			}
			
			@Override
			public void onNegativeClicked(Dialog dialog) {
				// TODO Auto-generated method stub
				ToastUtil.showToast(context, "取消点击");
			}
		});
		dialog.show();
	}
}
