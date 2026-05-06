package com.ipb.rental;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class SimpleLineChartView extends View {

    private Paint linePaint;
    private Paint fillPaint;
    private float[] dataPoints = {0.3f, 0.4f, 0.35f, 0.5f, 0.45f, 0.6f, 0.55f, 0.7f, 0.65f, 0.8f, 0.75f, 0.9f, 0.85f, 1.0f, 0.95f, 0.8f, 0.7f, 0.75f, 0.8f, 0.85f, 0.9f, 0.8f, 0.75f, 0.8f, 0.85f, 0.9f, 0.95f, 1.0f, 0.9f, 0.85f, 0.95f};

    public SimpleLineChartView(Context context) {
        super(context);
        init();
    }

    public SimpleLineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#1C2B4A"));
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataPoints == null || dataPoints.length == 0) return;

        float width = getWidth();
        float height = getHeight();
        float stepX = width / (dataPoints.length - 1);

        Path path = new Path();
        Path fillPath = new Path();

        for (int i = 0; i < dataPoints.length; i++) {
            float x = i * stepX;
            float y = height - (dataPoints[i] * height * 0.8f); // 0.8 to give some top padding

            if (i == 0) {
                path.moveTo(x, y);
                fillPath.moveTo(x, height);
                fillPath.lineTo(x, y);
            } else {
                path.lineTo(x, y);
                fillPath.lineTo(x, y);
            }
        }

        fillPath.lineTo(width, height);
        fillPath.close();

        fillPaint.setShader(new LinearGradient(0, 0, 0, height, Color.parseColor("#1C2B4A10"), Color.TRANSPARENT, Shader.TileMode.CLAMP));
        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(path, linePaint);
    }
}
