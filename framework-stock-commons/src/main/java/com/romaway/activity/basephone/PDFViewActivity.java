/**
 * Copyright 2014 Joan Zapata
 *
 * This file is part of Android-pdfview.
 *
 * Android-pdfview is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Android-pdfview is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Android-pdfview.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.romaway.activity.basephone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.romaway.android.phone.R;
import com.romaway.commons.lang.StringUtils;
import com.trevorpage.tpsvg.SVGView;
import com.trevorpage.tpsvg.SvgRes1;

import java.io.File;

import static java.lang.String.format;

public class PDFViewActivity extends Activity implements OnPageChangeListener {

    PDFView pdfView;

    String pdfName = " ";
    String pdfPath = "";
    Integer pageNumber = 1;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.roma_pdf_view_activity_layout);
        SVGView svgBackbtn = (SVGView) this.findViewById(R.id.svg_backbtn);
        title = (TextView) findViewById(R.id.title);
        // 返回按钮
        svgBackbtn.setSVGRenderer(
                SvgRes1.getSVGParserRenderer(this,
                        R.drawable.roma_news_btn_backbtn_normal), null);

        ((LinearLayout) this.findViewById(R.id.svg_backbtn_root)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        pdfView = (PDFView) this.findViewById(R.id.pdfView);
        Intent intent = getIntent();
        if(intent != null){
            pdfPath = intent.getStringExtra("PDFPath");
            if(!StringUtils.isEmpty(pdfPath)){
                afterViews();
                getPdfFileIntent(pdfPath);
            }
        }

    }

    /**
     * 读取SD卡上的pdf文件，并启动其他app进行阅读
     * @param path
     * @return
     */
    public void getPdfFileIntent(String path)
    {
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        File file = new File(path);
//        Uri uri = Uri.fromFile(file);
//      //  intent.setDataAndType(uri,"application/pdf");
//        startActivity(intent);
//
//        return intent;
    }

    @SuppressLint("NewApi")
    private File createChildsDir(File parentDir, String childPath){

        File file = null;
        if(parentDir.isDirectory()){
            String childDirPath = parentDir.getPath();
            String[] path = childPath.split("/");
            for(int i = 0; i < path.length;i++){

                childDirPath += "/" + path[i];

                file = new File(childDirPath);
                if(!file.exists()) {//考虑已经存在同样名字的文件或者目录，
                    file.mkdir();
                    file.setExecutable(true, false);
                    file.setReadable(true, false);
                    file.setWritable(true, false);
                }
            }
        }
        return file;
    }

    void afterViews() {
        display(pdfName, false);
    }


    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage) pageNumber = 1;
        setTitle(pdfName = assetFileName);

        pdfView.fromFile(new File(pdfPath))
                .swipeVertical(true)// 水平查看还是竖屏查看
                .defaultPage(pageNumber)
                .onPageChange(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(format("%s %s / %s", pdfName, page, pageCount));
    }

    public void setTitle(String strtitle) {
        title.setText(strtitle);
    }
}
