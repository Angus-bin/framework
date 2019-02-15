/*
 * Copyright (C) cuiweiyou.com/崔维友
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package roma.romaway.commons.android.tougu.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.romaway.android.phone.R;
import com.romaway.commons.log.Logger;

/**
 * A dialog that prompts the user for the number using a NumberPicker.<br/>
 * 使用NumberPicker获取数值的对话框
 */
public class NumberPickerDialog extends AlertDialog implements OnClickListener, NumberPicker.OnValueChangeListener {

    private final String maxValue = "最大值";
    private final String minValue = "最小值";
    private final String currentValue = "当前值";

    private final NumberPicker mNumberPicker;
    private final NumberPicker.OnValueChangeListener mCallback;

    private int newVal;
    private int oldVal;

    /**
     * @param context      上下文
     * @param callBack     回调器
     * @param maxValue     最大值
     * @param minValue     最小值
     * @param currentValue 当前值
     */
    public NumberPickerDialog(Context context, NumberPicker.OnValueChangeListener callBack, int maxValue, int minValue, int currentValue) {
        this(context, callBack, maxValue, minValue, currentValue, "设置数字", "设置", "取消");
    }

    /**
     * @param context      上下文
     * @param callBack     回调器
     * @param maxValue     最大值
     * @param minValue     最小值
     * @param currentValue 当前值
     * @param titleMsg     提示标题
     * @param positiveBtn  确定按钮文本
     * @param negativeBtn  取消按钮文本
     */
    public NumberPickerDialog(Context context, NumberPicker.OnValueChangeListener callBack,
                              int maxValue, int minValue, int currentValue,
                              String titleMsg, String positiveBtn, String negativeBtn) {
        super(context, AlertDialog.THEME_HOLO_LIGHT);

        mCallback = callBack;
        setIcon(0);
        setTitle(titleMsg);

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, positiveBtn, this);
        // setButton(BUTTON_NEGATIVE, negativeBtn, this);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_number_picker_dialog, null);
        setView(view);

        mNumberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);

        mNumberPicker.setMaxValue(maxValue);
        mNumberPicker.setMinValue(minValue);
        mNumberPicker.setValue(currentValue);
        mNumberPicker.setOnValueChangedListener(this);
    }

    /**
     * @param context      上下文
     * @param callBack     回调器
     * @param maxValue     最大值
     * @param minValue     最小值
     * @param currentValue 当前值
     * @param titleMsg     提示标题
     * @param positiveBtn  确定按钮文本
     * @param negativeBtn  取消按钮文本
     * @param value         选择内容
     */
    public NumberPickerDialog(Context context, NumberPicker.OnValueChangeListener callBack,
                              int maxValue, int minValue, int currentValue,
                              String titleMsg, String positiveBtn, String negativeBtn, String[] value) {
        super(context, AlertDialog.THEME_HOLO_LIGHT);

        mCallback = callBack;
        setIcon(0);
        setTitle(titleMsg);

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, positiveBtn, this);
        // setButton(BUTTON_NEGATIVE, negativeBtn, this);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_number_picker_dialog, null);
        setView(view);
        Logger.d("NumberPickerDialog", "value = " + value);
        mNumberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        mNumberPicker.setDisplayedValues(value);
        mNumberPicker.setMaxValue(maxValue);
        mNumberPicker.setMinValue(minValue);
        mNumberPicker.setValue(currentValue);
        mNumberPicker.setOnValueChangedListener(this);
    }


    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(maxValue, mNumberPicker.getMaxValue());
        state.putInt(minValue, mNumberPicker.getMinValue());
        state.putInt(currentValue, mNumberPicker.getValue());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int max = savedInstanceState.getInt(maxValue);
        int min = savedInstanceState.getInt(minValue);
        int cur = savedInstanceState.getInt(currentValue);
        mNumberPicker.setMaxValue(max);
        mNumberPicker.setMinValue(min);
        mNumberPicker.setValue(cur);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        this.oldVal = oldVal;
        this.newVal = newVal;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                mCallback.onValueChange(mNumberPicker, oldVal, newVal);
                break;
        }
    }

    /**
     * <b>功能</b>: setCurrentValue，设置NumberPicker的当前值<br/>
     * @return
     */
    public NumberPickerDialog setCurrentValue(int value) {
        mNumberPicker.setValue(value);
        return this;
    }

    /**
     * <b>功能</b>: setMaxValue，设置NumberPicker的最大值<br/>
     * @return
     */
    public NumberPickerDialog setMaxValue(int maxValue) {
        mNumberPicker.setMaxValue(maxValue);
        return this;
    }

    /**
     * <b>功能</b>: setMinValue，设置NumberPicker的最小值<br/>
     * @return
     */
    public NumberPickerDialog setMinValue(int minValue) {
        mNumberPicker.setMinValue(minValue);
        return this;
    }
}