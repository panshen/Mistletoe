//package com.helper.mistletoe.zxing;
//
//import java.io.ByteArrayOutputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.Hashtable;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.helper.olivine.R;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.provider.Settings.System;
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * ��ά������ ���ɵĶ�ά���� �ڴ濨��Lidynast/pic/��
// * 
// * @author LJC
// * 
// */
//public class ZxingActivity extends Activity {
//	private final static String TAG = "Lidyanst_��ά������";
//
//	static String mPath = Environment.getExternalStorageDirectory()
//			+ "/lidynast/pic/";
//	static String mFileName = java.lang.System.currentTimeMillis() + ".png";
//	// ǰ��ɫ
//	int FOREGROUND_COLOR = 0xff000000;
//	// ����ɫ
//	int BACKGROUND_COLOR = 0xffffffff;
//
//	// �������
//	private ImageView imgShow = null;
//	private ImageView iconShow = null;
//	private EditText et = null;
//
//	private final String IMAGE_TYPE = "image/*";
//
//	private final int IMAGE_CODE = 0; // �����IMAGE_CODE���Լ����ⶨ���
//	// ͼƬ��ȵ�һ��
//	private static final int IMAGE_HALFWIDTH = 30;
//
//	// ���뵽��ά�������ͼƬ����
//	private Bitmap mIcon;
//	// ��Ҫ��ͼͼƬ�Ĵ�С �����趨Ϊ40*40
//	int[] mPixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		mIcon = BitmapFactory.decodeResource(getResources(),
//				R.drawable.app_logo);
//		imgShow = (ImageView) findViewById(R.id.img);
//		iconShow = (ImageView) findViewById(R.id.icon);
//		et = (EditText) findViewById(R.id.et);
//	}
//
//	/**
//	 * ��ť����¼�-����ǰ��ɫ
//	 * 
//	 * @param v
//	 */
//	public void onQJS(View v) {
//		ColorPickerDialog dialog = new ColorPickerDialog(this, "����ǰ��ɫ",
//				new ColorPickerDialog.OnColorChangedListener() {
//					@Override
//					public void colorChanged(int color) {
//						FOREGROUND_COLOR = color;
//					}
//				});
//		dialog.show();
//	}
//
//	/**
//	 * ��ť����¼�-���ñ���ɫ
//	 * 
//	 * @param v
//	 */
//	public void onBJS(View v) {
//		ColorPickerDialog dialog = new ColorPickerDialog(this, "���ñ���ɫ",
//				new ColorPickerDialog.OnColorChangedListener() {
//					@Override
//					public void colorChanged(int color) {
//						BACKGROUND_COLOR = color;
//					}
//				});
//		dialog.show();
//	}
//
//	/**
//	 * ��ť����¼�-�л��м�ͼƬ
//	 * 
//	 * @param v
//	 */
//	public void onQHI(View v) {
//		setImage();
//	}
//
//	/**
//	 * ��ť����¼�-���ɶ�ά��
//	 * 
//	 * @param v
//	 */
//	public void onSC(View v) {
//		String content = et.getText().toString();
//		if (null != content && !content.equals("")) {
//			try {
//				Bitmap bitmap = cretaeBitmap(new String(content.getBytes(),
//						"UTF-8"), mIcon);
//				// ����ά��д���ڴ濨
//				writeBitmap(bitmap);
//				imgShow.setImageBitmap(bitmap);
//			} catch (UnsupportedEncodingException e) {
//				Log.d(TAG, e.getMessage());
//				Toast.makeText(this, "����", 3000).show();
//			} catch (WriterException e) {
//				Log.d(TAG, e.getMessage());
//				Toast.makeText(this, "����", 3000).show();
//			}
//		} else {
//			Toast.makeText(this, "��������Ϣ��", 3000).show();
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode != RESULT_OK) {
//			// �˴��� RESULT_OK ��ϵͳ�Զ����һ������
//			Log.e("TAG->onresult", "ActivityResult resultCode error");
//			return;
//		}
//		ContentResolver resolver = getContentResolver();
//		// �˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�
//		if (requestCode == IMAGE_CODE) {
//			try {
//				Uri originalUri = data.getData(); // ���ͼƬ��uri
//				mIcon = MediaStore.Images.Media
//						.getBitmap(resolver, originalUri);
//				iconShow.setImageBitmap(mIcon);
//			} catch (Exception e) {
//				Log.e("TAG-->Error", e.toString());
//
//			}
//		}
//	}
//
//	/**
//	 * ��ȡ�û�ѡ���ͼƬ
//	 * 
//	 * @param content
//	 */
//	private void setImage() {
//		// TODO Auto-generated method stub
//		// ʹ��intent����ϵͳ�ṩ����Ṧ�ܣ�ʹ��startActivityForResult��Ϊ�˻�ȡ�û�ѡ���ͼƬ
//		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//		getAlbum.setType(IMAGE_TYPE);
//		startActivityForResult(getAlbum, IMAGE_CODE);
//	}
//
//	/**
//	 * ���ɶ�ά�� �м����СͼƬ
//	 * 
//	 * @param str
//	 *            ����
//	 * @return Bitmap
//	 * @throws WriterException
//	 */
//	public Bitmap cretaeBitmap(String str, Bitmap icon) throws WriterException {
//		// ����һ��40*40��ͼƬ
//		icon = Untilly.zoomBitmap(icon, IMAGE_HALFWIDTH);
//		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		hints.put(EncodeHintType.MARGIN, 1);
//		// ���ɶ�ά����,����ʱָ����С,��Ҫ������ͼƬ�Ժ��ٽ�������,������ģ������ʶ��ʧ��
//		BitMatrix matrix = new MultiFormatWriter().encode(str,
//				BarcodeFormat.QR_CODE, 300, 300, hints);
//		int width = matrix.getWidth();
//		int height = matrix.getHeight();
//		// ��ά����תΪһά��������,Ҳ����һֱ��������
//		int halfW = width / 2;
//		int halfH = height / 2;
//		int[] pixels = new int[width * height];
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//						&& y > halfH - IMAGE_HALFWIDTH
//						&& y < halfH + IMAGE_HALFWIDTH) {
//					pixels[y * width + x] = icon.getPixel(x - halfW
//							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
//				} else {
//					if (matrix.get(x, y)) {
//						pixels[y * width + x] = FOREGROUND_COLOR;
//					} else { // ����Ϣ�������ص�Ϊ��ɫ
//						pixels[y * width + x] = BACKGROUND_COLOR;
//					}
//				}
//
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		// ͨ��������������bitmap
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//
//		return bitmap;
//	}
//
//	/**
//	 * �����ά��
//	 * 
//	 * @param b
//	 * @return
//	 */
//	public static boolean writeBitmap(Bitmap b) {
//		ByteArrayOutputStream by = new ByteArrayOutputStream();
//		b.compress(Bitmap.CompressFormat.PNG, 100, by);
//		byte[] stream = by.toByteArray();
//		return Untilly.writeToSdcard(stream, mPath, mFileName);
//	}
//
// }
