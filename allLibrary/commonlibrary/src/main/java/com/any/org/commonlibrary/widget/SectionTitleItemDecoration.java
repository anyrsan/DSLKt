package com.any.org.commonlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.any.org.commonlibrary.R;
import com.any.org.commonlibrary.log.KLog;
import com.any.org.commonlibrary.model.SectionModel;
import com.any.org.commonlibrary.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class SectionTitleItemDecoration extends RecyclerView.ItemDecoration {
    private List<SectionModel> mData;
    private Paint mBgPaint;
    private TextPaint mTextPaint;
    private Rect mBounds;

    private int mSectionHeight;
    private int mTextSize;

    public SectionTitleItemDecoration(Context context, List<SectionModel> data) {
        setData(data);
        mSectionHeight = DensityUtil.dp2px(context.getResources(), 35f);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(context.getResources().getColor(R.color.color_ffffff));

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(context.getResources().getColor(R.color.color_666666));

        mTextSize = DensityUtil.sp2px(context, 17f);
        mTextPaint.setTextSize(mTextSize);

        mBounds = new Rect();
    }

    public void setData(@Nullable List<SectionModel> data) {
        this.mData = data == null ? new ArrayList() : data;
    }

    public void addData(@NonNull List<SectionModel> data) {
        KLog.INSTANCE.e("section 输出");
        mData.addAll(data);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (mData != null && !mData.isEmpty() && position <= mData.size() - 1 && position > -1) {
                if (position == 0) {

                } else if (null != mData.get(position).sectionTitle()
                        && !mData.get(position).sectionTitle().equals(mData.get(position - 1).sectionTitle())) {
                    drawSection(c, left, right, child, params, position);
                }
            }
        }
    }

    private void drawSection(Canvas c, int left, int right, View child,
                             RecyclerView.LayoutParams params, int position) {
        String word = mData.get(position).sectionTitle();
        c.drawRect(left,
                child.getTop() - params.topMargin - mSectionHeight,
                right,
                child.getTop() - params.topMargin, mBgPaint);
        mTextPaint.getTextBounds(word,
                0,
                word.length(),
                mBounds);
        c.drawText(word,
                child.getLeft() - params.leftMargin - (child.getWidth() / 2 - mBounds.width() / 2),
                child.getTop() - params.topMargin - (mSectionHeight / 2 - mBounds.height() / 2),
                mTextPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (mData != null && !mData.isEmpty() && position <= mData.size() - 1 && position > -1) {
            if (position == 0) {
                outRect.set(0, mSectionHeight, 0, 0);
            } else {
                if (null != mData.get(position).sectionTitle()
                        && !mData.get(position).sectionTitle().equals(mData.get(position - 1).sectionTitle())) {
                    outRect.set(0, mSectionHeight, 0, 0);
                }
            }
        }

    }

}
