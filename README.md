# cardswitch 自定义控件
@Author : suzhiwei 
### 简要说明：
>CardSwitch 控件 为图片切换特效控件
>
![image](https://github.com/littlefishwill/cardswitch/blob/master/cradSwitch/src/main/res/drawable-hdpi/demoshow.gif)

### 使用 方法：
>1.在xml layout 布局文件中引用：

    <com.szw.cradswitch.CardView
    android:id="@+id/cardview"
    android:layout_width="500dip"
    android:layout_height="500dip"
    android:layout_centerInParent="true"
    android:clipChildren="false"></com.szw.cradswitch.CardView>

>2. 在代码中需要设置adapter


    //1.找到控件
	CardView cardView = (CardView) this.findViewById(R.id.cardview);
	//2 .创建一个adapter 继承CardViewAdapter
    adapter = new MyCardView(getApplicationContext());
	//3.填写 data 数据类型为 List<T> T为泛型
    adapter.setData(initData());
	//4. 设置adapter
    cardView.setAdapter(adapter);
    //5. 设置adapter 切换监听器
    cardView.setCardClickListener(new CardClickListener() {
    @Override
    public void onClick(View view, int pos) {
    // TODO Auto-generated method stub
    android.widget.Toast.makeText(getApplicationContext(), pos + "", 1).show();
    }
    });

>3 其他API  

	// 设置 cardView 的最大卡片层叠数量 默认为3 
	cardView.setmMaxCardSize(4);
	// 设置 cardView 的卡片间距  默认为20px （建议项目中 用dp，动态转换为px 传入） 
 	cardView.setItemSpace(20);



