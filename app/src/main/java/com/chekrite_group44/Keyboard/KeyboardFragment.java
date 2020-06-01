/*
 * Date: 2020.5.11
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Keyboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.R;

public class KeyboardFragment extends Fragment {


    //    private static final String TAG = "KeyboardFragment";
    Button mainActivity;
    Button a_btn;
    Button b_btn;
    Button c_btn;
    Button d_btn;
    Button e_btn;
    Button f_btn;
    Button g_btn;
    Button h_btn;
    Button i_btn;
    Button j_btn;
    Button k_btn;
    Button l_btn;
    Button m_btn;
    Button n_btn;
    Button o_btn;
    Button p_btn;
    Button q_btn;
    Button r_btn;
    Button s_btn;
    Button t_btn;
    Button u_btn;
    Button v_btn;
    Button w_btn;
    Button x_btn;
    Button y_btn;
    Button z_btn;
    Button backspace_btn;
    Button aToZ_btn;
    Button zeroTo9_btn;
    Button one_btn;
    Button two_btn;
    Button three_btn;
    Button four_btn;
    Button five_btn;
    Button six_btn;
    Button seven_btn;
    Button eight_btn;
    Button nine_btn;
    Button zero_btn;
    Button fullstop_btn;
    Button dash_btn;
    Button leftBracket_btn;
    Button rightBracket_btn;
    Button space_btn;
    Button num_backspace_btn;


    private KeyboardFragmentListener listener;


    public interface KeyboardFragmentListener {
        void onInputKeyboardSent (CharSequence input);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keyboard_fragment, container, false);

        //alphabet keyboard (A-Z)
        a_btn = (Button) view.findViewById(R.id.a_btn);
        a_btn.setOnClickListener(keyboardListener);
        b_btn = (Button) view.findViewById(R.id.b_btn);
        b_btn.setOnClickListener(keyboardListener);
        c_btn = (Button) view.findViewById(R.id.c_btn);
        c_btn.setOnClickListener(keyboardListener);
        d_btn = (Button) view.findViewById(R.id.d_btn);
        d_btn.setOnClickListener(keyboardListener);
        e_btn = (Button) view.findViewById(R.id.e_btn);
        e_btn.setOnClickListener(keyboardListener);
        f_btn = (Button) view.findViewById(R.id.f_btn);
        f_btn.setOnClickListener(keyboardListener);
        g_btn = (Button) view.findViewById(R.id.g_btn);
        g_btn.setOnClickListener(keyboardListener);
        h_btn = (Button) view.findViewById(R.id.h_btn);
        h_btn.setOnClickListener(keyboardListener);
        i_btn = (Button) view.findViewById(R.id.i_btn);
        i_btn.setOnClickListener(keyboardListener);
        j_btn = (Button) view.findViewById(R.id.j_btn);
        j_btn.setOnClickListener(keyboardListener);
        k_btn = (Button) view.findViewById(R.id.k_btn);
        k_btn.setOnClickListener(keyboardListener);
        l_btn = (Button) view.findViewById(R.id.l_btn);
        l_btn.setOnClickListener(keyboardListener);
        m_btn = (Button) view.findViewById(R.id.m_btn);
        m_btn.setOnClickListener(keyboardListener);
        n_btn = (Button) view.findViewById(R.id.n_btn);
        n_btn.setOnClickListener(keyboardListener);
        o_btn = (Button) view.findViewById(R.id.o_btn);
        o_btn.setOnClickListener(keyboardListener);
        p_btn = (Button) view.findViewById(R.id.p_btn);
        p_btn.setOnClickListener(keyboardListener);
        q_btn = (Button) view.findViewById(R.id.q_btn);
        q_btn.setOnClickListener(keyboardListener);
        r_btn = (Button) view.findViewById(R.id.r_btn);
        r_btn.setOnClickListener(keyboardListener);
        s_btn = (Button) view.findViewById(R.id.s_btn);
        s_btn.setOnClickListener(keyboardListener);
        t_btn = (Button) view.findViewById(R.id.t_btn);
        t_btn.setOnClickListener(keyboardListener);
        u_btn = (Button) view.findViewById(R.id.u_btn);
        u_btn.setOnClickListener(keyboardListener);
        v_btn = (Button) view.findViewById(R.id.v_btn);
        v_btn.setOnClickListener(keyboardListener);
        w_btn = (Button) view.findViewById(R.id.w_btn);
        w_btn.setOnClickListener(keyboardListener);
        x_btn = (Button) view.findViewById(R.id.x_btn);
        x_btn.setOnClickListener(keyboardListener);
        y_btn = (Button) view.findViewById(R.id.y_btn);
        y_btn.setOnClickListener(keyboardListener);
        z_btn = (Button) view.findViewById(R.id.z_btn);
        z_btn.setOnClickListener(keyboardListener);
        backspace_btn = (Button) view.findViewById(R.id.backspace_btn);
        backspace_btn.setOnClickListener(keyboardListener);
        aToZ_btn = (Button) view.findViewById(R.id.aToZ_btn);
        zeroTo9_btn = (Button) view.findViewById(R.id.zeroTo9_btn);


        //Numbers keyboard (0-9)
        one_btn = (Button) view.findViewById(R.id.one_btn);
        one_btn.setOnClickListener(keyboardListener);
        two_btn = (Button) view.findViewById(R.id.two_btn);
        two_btn.setOnClickListener(keyboardListener);
        three_btn = (Button) view.findViewById(R.id.three_btn);
        three_btn.setOnClickListener(keyboardListener);
        four_btn = (Button) view.findViewById(R.id.four_btn);
        four_btn.setOnClickListener(keyboardListener);
        five_btn = (Button) view.findViewById(R.id.five_btn);
        five_btn.setOnClickListener(keyboardListener);
        six_btn = (Button) view.findViewById(R.id.six_btn);
        six_btn.setOnClickListener(keyboardListener);
        seven_btn = (Button) view.findViewById(R.id.seven_btn);
        seven_btn.setOnClickListener(keyboardListener);
        eight_btn = (Button) view.findViewById(R.id.eight_btn);
        eight_btn.setOnClickListener(keyboardListener);
        nine_btn = (Button) view.findViewById(R.id.nine_btn);
        nine_btn.setOnClickListener(keyboardListener);
        zero_btn = (Button) view.findViewById(R.id.zero_btn);
        zero_btn.setOnClickListener(keyboardListener);
        fullstop_btn = (Button) view.findViewById(R.id.fullstop_btn);
        fullstop_btn.setOnClickListener(keyboardListener);
        dash_btn = (Button) view.findViewById(R.id.dash_btn);
        dash_btn.setOnClickListener(keyboardListener);
        leftBracket_btn = (Button) view.findViewById(R.id.leftBracket_btn);
        leftBracket_btn.setOnClickListener(keyboardListener);
        rightBracket_btn = (Button) view.findViewById(R.id.rightBracket_btn);
        rightBracket_btn.setOnClickListener(keyboardListener);
        space_btn = (Button) view.findViewById(R.id.space_btn);
        space_btn.setOnClickListener(keyboardListener);
        num_backspace_btn = (Button) view.findViewById(R.id.backspace1_btn);
        num_backspace_btn.setOnClickListener(keyboardListener);


        //show letters keyboard first when fragment is used
        hideNumbersButton();
        aToZ_btn.setActivated(true);

        //Shows numbers keyboard when (0-9) button pressed
        zeroTo9_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumbersKeyboard();
            }
        });

        //Shows letters keyboard when (a-z) button is pressed
        aToZ_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLettersKeyboard();
            }
        });

        return view;

    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof KeyboardFragmentListener) {
            listener = (KeyboardFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement keyboardFragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public View.OnClickListener keyboardListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.a_btn:
                    listener.onInputKeyboardSent("A");
                    break;
                case R.id.b_btn:
                    listener.onInputKeyboardSent("B");
                    break;
                case R.id.c_btn:
                    listener.onInputKeyboardSent("C");
                    break;
                case R.id.d_btn:
                    listener.onInputKeyboardSent("D");
                    break;
                case R.id.e_btn:
                    listener.onInputKeyboardSent("E");
                    break;
                case R.id.f_btn:
                    listener.onInputKeyboardSent("F");
                    break;
                case R.id.g_btn:
                    listener.onInputKeyboardSent("G");
                    break;
                case R.id.h_btn:
                    listener.onInputKeyboardSent("H");
                    break;
                case R.id.i_btn:
                    listener.onInputKeyboardSent("I");
                    break;
                case R.id.j_btn:
                    listener.onInputKeyboardSent("J");
                    break;
                case R.id.k_btn:
                    listener.onInputKeyboardSent("K");
                    break;
                case R.id.l_btn:
                    listener.onInputKeyboardSent("L");
                    break;
                case R.id.m_btn:
                    listener.onInputKeyboardSent("M");
                    break;
                case R.id.n_btn:
                    listener.onInputKeyboardSent("N");
                    break;
                case R.id.o_btn:
                    listener.onInputKeyboardSent("O");
                    break;
                case R.id.p_btn:
                    listener.onInputKeyboardSent("P");
                    break;
                case R.id.q_btn:
                    listener.onInputKeyboardSent("Q");
                    break;
                case R.id.r_btn:
                    listener.onInputKeyboardSent("R");
                    break;
                case R.id.s_btn:
                    listener.onInputKeyboardSent("S");
                    break;
                case R.id.t_btn:
                    listener.onInputKeyboardSent("T");
                    break;
                case R.id.u_btn:
                    listener.onInputKeyboardSent("U");
                    break;
                case R.id.v_btn:
                    listener.onInputKeyboardSent("V");
                    break;
                case R.id.w_btn:
                    listener.onInputKeyboardSent("W");
                    break;
                case R.id.x_btn:
                    listener.onInputKeyboardSent("X");
                    break;
                case R.id.y_btn:
                    listener.onInputKeyboardSent("Y");
                    break;
                case R.id.z_btn:
                    listener.onInputKeyboardSent("Z");
                    break;
                case R.id.backspace_btn:
                    listener.onInputKeyboardSent("/");
                    break;
                case R.id.one_btn:
                    listener.onInputKeyboardSent("1");
                    break;
                case R.id.two_btn:
                    listener.onInputKeyboardSent("2");
                    break;
                case R.id.three_btn:
                    listener.onInputKeyboardSent("3");
                    break;
                case R.id.four_btn:
                    listener.onInputKeyboardSent("4");
                    break;
                case R.id.five_btn:
                    listener.onInputKeyboardSent("5");
                    break;
                case R.id.six_btn:
                    listener.onInputKeyboardSent("6");
                    break;
                case R.id.seven_btn:
                    listener.onInputKeyboardSent("7");
                    break;
                case R.id.eight_btn:
                    listener.onInputKeyboardSent("8");
                    break;
                case R.id.nine_btn:
                    listener.onInputKeyboardSent("9");
                    break;
                case R.id.zero_btn:
                    listener.onInputKeyboardSent("0");
                    break;
                case R.id.fullstop_btn:
                    listener.onInputKeyboardSent(".");
                    break;
                case R.id.dash_btn:
                    listener.onInputKeyboardSent("-");
                    break;
                case R.id.leftBracket_btn:
                    listener.onInputKeyboardSent("(");
                    break;
                case R.id.rightBracket_btn:
                    listener.onInputKeyboardSent(")");
                    break;
                case R.id.space_btn:
                    listener.onInputKeyboardSent(" ");
                    break;
                case R.id.backspace1_btn:
                    listener.onInputKeyboardSent("/");
                    break;
            }
        }
    };


    public void showLettersKeyboard() {
        showLettersButton();
        hideNumbersButton();
        aToZ_btn.setActivated(true);
        zeroTo9_btn.setActivated(false);
    }

    public void showNumbersKeyboard() {
        showNumbersButton();
        hideLettersButton();
        aToZ_btn.setActivated(false);
        zeroTo9_btn.setActivated(true);
    }


    public void showLettersButton() {
        a_btn.setVisibility(View.VISIBLE);
        b_btn.setVisibility(View.VISIBLE);
        c_btn.setVisibility(View.VISIBLE);
        d_btn.setVisibility(View.VISIBLE);
        e_btn.setVisibility(View.VISIBLE);
        f_btn.setVisibility(View.VISIBLE);
        g_btn.setVisibility(View.VISIBLE);
        h_btn.setVisibility(View.VISIBLE);
        i_btn.setVisibility(View.VISIBLE);
        j_btn.setVisibility(View.VISIBLE);
        k_btn.setVisibility(View.VISIBLE);
        l_btn.setVisibility(View.VISIBLE);
        m_btn.setVisibility(View.VISIBLE);
        n_btn.setVisibility(View.VISIBLE);
        o_btn.setVisibility(View.VISIBLE);
        p_btn.setVisibility(View.VISIBLE);
        q_btn.setVisibility(View.VISIBLE);
        r_btn.setVisibility(View.VISIBLE);
        s_btn.setVisibility(View.VISIBLE);
        t_btn.setVisibility(View.VISIBLE);
        u_btn.setVisibility(View.VISIBLE);
        v_btn.setVisibility(View.VISIBLE);
        w_btn.setVisibility(View.VISIBLE);
        x_btn.setVisibility(View.VISIBLE);
        y_btn.setVisibility(View.VISIBLE);
        z_btn.setVisibility(View.VISIBLE);
        backspace_btn.setVisibility(View.VISIBLE);
    }

    public void hideLettersButton() {
        a_btn.setVisibility(View.GONE);
        b_btn.setVisibility(View.GONE);
        c_btn.setVisibility(View.GONE);
        d_btn.setVisibility(View.GONE);
        e_btn.setVisibility(View.GONE);
        f_btn.setVisibility(View.GONE);
        g_btn.setVisibility(View.GONE);
        h_btn.setVisibility(View.GONE);
        i_btn.setVisibility(View.GONE);
        j_btn.setVisibility(View.GONE);
        k_btn.setVisibility(View.GONE);
        l_btn.setVisibility(View.GONE);
        m_btn.setVisibility(View.GONE);
        n_btn.setVisibility(View.GONE);
        o_btn.setVisibility(View.GONE);
        p_btn.setVisibility(View.GONE);
        q_btn.setVisibility(View.GONE);
        r_btn.setVisibility(View.GONE);
        s_btn.setVisibility(View.GONE);
        t_btn.setVisibility(View.GONE);
        u_btn.setVisibility(View.GONE);
        v_btn.setVisibility(View.GONE);
        w_btn.setVisibility(View.GONE);
        x_btn.setVisibility(View.GONE);
        y_btn.setVisibility(View.GONE);
        z_btn.setVisibility(View.GONE);
        backspace_btn.setVisibility(View.GONE);
    }


    public void hideNumbersButton(){
        one_btn.setVisibility(View.GONE);
        two_btn.setVisibility(View.GONE);
        three_btn.setVisibility(View.GONE);
        four_btn.setVisibility(View.GONE);
        five_btn.setVisibility(View.GONE);
        six_btn.setVisibility(View.GONE);
        seven_btn.setVisibility(View.GONE);
        eight_btn.setVisibility(View.GONE);
        nine_btn.setVisibility(View.GONE);
        zero_btn.setVisibility(View.GONE);
        fullstop_btn.setVisibility(View.GONE);
        dash_btn.setVisibility(View.GONE);
        leftBracket_btn.setVisibility(View.GONE);
        rightBracket_btn.setVisibility(View.GONE);
        space_btn.setVisibility(View.GONE);
        num_backspace_btn.setVisibility(View.GONE);
    }

    public void showNumbersButton(){
        one_btn.setVisibility(View.VISIBLE);
        two_btn.setVisibility(View.VISIBLE);
        three_btn.setVisibility(View.VISIBLE);
        four_btn.setVisibility(View.VISIBLE);
        five_btn.setVisibility(View.VISIBLE);
        six_btn.setVisibility(View.VISIBLE);
        seven_btn.setVisibility(View.VISIBLE);
        eight_btn.setVisibility(View.VISIBLE);
        nine_btn.setVisibility(View.VISIBLE);
        zero_btn.setVisibility(View.VISIBLE);
        fullstop_btn.setVisibility(View.VISIBLE);
        dash_btn.setVisibility(View.VISIBLE);
        leftBracket_btn.setVisibility(View.VISIBLE);
        rightBracket_btn.setVisibility(View.VISIBLE);
        space_btn.setVisibility(View.VISIBLE);
        num_backspace_btn.setVisibility(View.VISIBLE);
    }
}
