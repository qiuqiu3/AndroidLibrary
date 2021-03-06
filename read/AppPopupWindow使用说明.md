## AppPopupWindow使用说明

### 概述
AppPopupWindow是我对PopupWindow进行一个简单封装形成的一个类。其中集成了PopupWindow创建所需要的基本方法，包括背景遮罩设置，显示尺寸，显示位置，出场动画等。当我们需要快速创建一个PopupWindow的时候，我们可以继承AppPopupWindow。

### 使用说明
#### 一. 如何快速建一个PopupWindow
基于AppPopupWindow特性，我们可以继承AppPopupWindow快速创建自己的PopupWindow。以TestPop为例，我们可以类似这样创建：
```
public class TestPop extends AppPopupWindow {

    private Button mButton;

    public TestPop(Context context){
        super(context);

        //传值逻辑
        //......

        //初始化(这里必须调用,不然initView,initData和setListener三个方法不执行)
        initView();
        initData();
        setListener();
    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.5d,super.WRAP_CONTENT};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_test;
    }

    @Override
    protected void initView() {
        mButton= (Button) getView(R.id.btn1);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {

    }


    public void setBtnConfirm(View.OnClickListener listener){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }
}
```
#### 二.设置PopupWindow显示大小
以上面TestPop为例，TestPop显示大小由以下方法决定：
```
    protected double[] getWindowSize() {
        return new double[]{d1,d2};
    }
```
其中d1和d2是double类型。
##### 2.1 设置TestPop全屏显示
全屏设置可能存在兼容性问题，慎用，若不得已需要使用，需要多加测试。
```
public class TestPop extends AppPopupWindow {

    public TestPop(Context context){
        super(context);

    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{super.MATCH_PARENT,super.MATCH_PARENT};
    }
    
    //其他方法省略
    //......

}
```
##### 2.2 设置TestPop大小自适应
若要设置TestPop的大小自适应，你可以这样：
```
public class TestPop extends AppPopupWindow {

    public TestPop(Context context){
        super(context);

    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{super.WRAP_CONTENT,super.WRAP_CONTENT};
    }
    
    //其他方法省略
    //......

}
```
##### 2.3 设置TestPop按屏幕尺寸比例显示大小
若要让TestPop按屏幕比例显示，你可以这样：
```
public class TestPop extends AppPopupWindow {

    public TestPop(Context context){
        super(context);

    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.3d,0.25d};
    }
    
    //其他方法省略
    //......

}
```
##### 2.4 设置TestPop的最大高度
一般我们可能还会有这样的需求，就是在`pop`在某高度范围内则自适应显示，超过某高度则按此高度显示。  
若有这样的需求，可使用如下方法做设置:
```
    /**
     * 设置PopupWindow最大高度
     *
     * @param scaleHeight 高度系数，float类型
     *                    eg：  scaleHeight=0.5f 表示高度是屏幕高度的 0.5
     * @return
     */
    public AppPopupWindow setMaxViewHeight(float scaleHeight)
```
该方法在你自建的`pop`初始化时设置。如我限定在自定义`pop`即以上的`TestPop`显示的最大高度不超过屏幕的二分之一(即`TestPop`高度小于屏幕高度一半时
按实际高度显示，当`TestPop`高度大于屏幕高度一半时，则按屏幕高度一般设置)，则可以在`TestPop`初始化时作设置，示例代码如下:
```
/**
 * Title:限制popuwindow的最大高度的pop
 * description:
 * autor:pei
 * created on 2020/6/2
 */
public class TestPop extends AppPopupWindow {

    public TestPop(Context context) {
        super(context);

        //传值逻辑
        //......

        //初始化(这里必须调用,不然initView,initData和setListener三个方法不执行)
        initView();
        initData();
        setListener();
    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.5d, super.WRAP_CONTENT};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pop;
    }

    @Override
    protected void initView() {
        
    }

    @Override
    protected void initData() {
        //设置最大高度为屏幕高度的一半
        setMaxViewHeight(0.5f);

    }

    @Override
    protected void setListener() {

    }

}
```
同理，设置最大宽度可用以下方法:
```
    /**
     * 设置PopupWindow最大宽度
     *
     * @param scaleWidth 宽度系数，float类型
     *                    eg：  scaleWidth=0.5f 表示宽度是屏幕宽度的 0.5
     * @return
     */
    public AppPopupWindow setMaxViewWidth(float scaleWidth)
```
这里需要注意的是，一般当有最大 宽度/高度 设置的时候，则`TestPop`的 宽度/高度 在初始化方法`getWindowSize()`种设置的参数会是`super.WRAP_CONTENT`,
然后设置最大宽度/高度 的方法会在`TestPop`初始化时做设置。
#### 三. PopupWindow中控件初始化
如上所述，在TestPop中，你可以这样像初始化控件：
```
    //声明控件
    private Button mButton;

    //初始化控件
    mButton= (Button) getView(R.id.btn1);
```
#### 四.设置PopupWindow遮罩透明度
设置PopupWindow遮罩透明度方法如下：
```
    /**
     * 让popupwindown背景变暗
     *
     * @param bgAlpha 0f全黑，1.0f全透明
     * @return
     */
    setBackgroundAlpha(float bgAlpha)
```
例如，设置背景遮罩透明度为0.5f，可以这样：
```
popupWindow. setBackgroundAlpha(0.5f);
```
#### 五. 设置PopupWindow弹出动画
AppPopupWindow还支持弹出动画效果，具体动画效果参数有以下几种：
```
    //动画弹出效果
    public static final int ANIMATION_TOP_STYLE=1;//动画从控件上方弹出
    public static final int ANIMATION_LEFT_STYLE=2;//动画从控件左方弹出
    public static final int ANIMATION_BOTTOM_STYLE=3;//动画从控件下方弹出
    public static final int ANIMATION_RIGHT_STYLE=4;//动画从控件右方弹出
    public static final int ANIMATION_SCREEN_BOTTOM_STYLE=5;//动画从屏幕底部弹出
```
例如，我要设置popupWindow从控件上方弹出动画，可以这样设置：
```
//控件上面弹出
popupWindow.setAnimation(AppPopupWindow.ANIMATION_TOP_STYLE)
```
其他动画效果调用雷同，这里就不啰嗦了。
#### 六.设置PopupWindow弹出位置
AppPopupWindow支持popupWindow的弹出位置，其支持在控件上，下，左，右及屏幕底部弹出五种显示位置，调用方法如下：
```
    /**
     * 显示在控件正上方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationTop(View view, float marginDp)

    /**
     * 显示在控件正下方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationBottom(View view, float marginDp)

    /**
     * 显示在控件左方
     *
     * @param view 依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationLeft(View view, float marginDp)

    /**
     * 显示在控件右方
     *
     * @param view 依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationRight(View view, float marginDp)

    /**
     * 从屏幕底部弹出
     *
     * @param view
     */
    public void showAtScreenBottom(View view)
```
#### 七. AppPopupWindow其他方法
AppPopupWindow除了以上方法外，还有些常用的方法，如下：
```
/** PopupWindow背景设置 **/
setBackground(int color)

 /**点击pop外部是否消失，默认true消失**/
 setOuTouchCancel(boolean ouTouchCancel)

    /**
     * 获取焦点
     * 
     * @param focuse:设置为true时，可以让pop实现点击pop外部pop消失
     *               还可以让点击返回键时pop消失，一般设置true 
     * @return
     */
  setFocuse(boolean focuse)

  /**隐藏键盘**/
  hideKeyboard(View view)
```
#### 八.使用示例
##### 8.1 写一个popupwindow继承自AppPopupWindow
以TestPop为例，代码如下：
```
public class TestPop extends AppPopupWindow {

    //声明控件
    private Button mButton;

    public TestPop(Context context){
        super(context);
        
        //传值逻辑
        //......

        //初始化(这里必须调用,不然initView,initData和setListener三个方法不执行)
        initView();
        initData();
        setListener();
    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.3d,0.25d};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_test;
    }

    @Override
    protected void initView() {
        //初始化控件
        mButton= (Button) getView(R.id.btn1);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {

    }


    public void setBtnConfirm(View.OnClickListener listener){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }
}
```
##### 8.2 popupwindow在MainActivity中使用示例
还是以TestPop为例，调用示例如下：
```
    private void showPop(){
        TestPop testPop=new TestPop(MainActivity.this);

        testPop.setBackground(Color.RED)//设置背景
                .setFocuse(true)//点击返回键消失,一般设置为true
                .setOuTouchCancel(true);//点击pop外部消失，默认true消失
//                .setAnimation(AppPopupWindow.ANIMATION_TOP_STYLE)//控件上面弹出
//                .setAnimation(AppPopupWindow.ANIMATION_LEFT_STYLE)//控件左边弹出
//                .setAnimation(AppPopupWindow.ANIMATION_BOTTOM_STYLE)//控件下面弹出
//                .setAnimation(AppPopupWindow.ANIMATION_RIGHT_STYLE)//控件右边弹出
//                .setAnimation(AppPopupWindow.ANIMATION_SCREEN_BOTTOM_STYLE)//屏幕底部弹出
//                .setBackgroundAlpha(0.5f);//设置背景遮罩透明度(0f全黑，1.0f全透明)
        //点击事件
        testPop.setBtnConfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("lakkkkkkkkk");
            }
        });
        //监听关闭
        testPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //遮罩还原
                testPop.setBackgroundAlpha(1.0f);
                //关闭键盘
                testPop.hideKeyboard(mBtn1);
            }
        });
        //显示
        if (!testPop.isShowing()) {

//            //控件正上方弹出pop
//            testPop.showAtLocationTop(mBtn1,5);
//            //控件左方弹出pop
//            testPop.showAtLocationLeft(mBtn1,5);
//            //控件正下方弹出pop
//            testPop.showAtLocationBottom(mBtn1, 5);
//            //控件右方弹出pop
//            testPop.showAtLocationRight(mBtn1,5);
            //屏幕正下方弹出pop
            testPop.showAtScreenBottom(mBtn1);
//            //弹出键盘
//            KeyboardUtil.openKeybord(pop.getEdtPCH(), mContext);
        }

    }
```
