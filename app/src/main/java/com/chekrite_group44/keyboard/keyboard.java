/*
 * Date: 2020.5.11
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.keyboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.R;

public class keyboard extends Fragment {


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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keyboard_fragment, container, false);
//        mainActivity = (Button) view.findViewById(R.id.g_btn);
//
//        mainActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
        //alphabet keyboard (A-Z)
        a_btn = (Button) view.findViewById(R.id.a_btn);
        b_btn = (Button) view.findViewById(R.id.b_btn);
        c_btn = (Button) view.findViewById(R.id.c_btn);
        d_btn = (Button) view.findViewById(R.id.d_btn);
        e_btn = (Button) view.findViewById(R.id.e_btn);
        f_btn = (Button) view.findViewById(R.id.f_btn);
        g_btn = (Button) view.findViewById(R.id.g_btn);
        h_btn = (Button) view.findViewById(R.id.h_btn);
        i_btn = (Button) view.findViewById(R.id.i_btn);
        j_btn = (Button) view.findViewById(R.id.j_btn);
        k_btn = (Button) view.findViewById(R.id.k_btn);
        l_btn = (Button) view.findViewById(R.id.l_btn);
        m_btn = (Button) view.findViewById(R.id.m_btn);
        n_btn = (Button) view.findViewById(R.id.n_btn);
        o_btn = (Button) view.findViewById(R.id.o_btn);
        p_btn = (Button) view.findViewById(R.id.p_btn);
        q_btn = (Button) view.findViewById(R.id.q_btn);
        r_btn = (Button) view.findViewById(R.id.r_btn);
        s_btn = (Button) view.findViewById(R.id.s_btn);
        t_btn = (Button) view.findViewById(R.id.t_btn);
        u_btn = (Button) view.findViewById(R.id.u_btn);
        v_btn = (Button) view.findViewById(R.id.v_btn);
        w_btn = (Button) view.findViewById(R.id.w_btn);
        x_btn = (Button) view.findViewById(R.id.x_btn);
        y_btn = (Button) view.findViewById(R.id.y_btn);
        z_btn = (Button) view.findViewById(R.id.z_btn);
        backspace_btn = (Button) view.findViewById(R.id.backspace_btn);
        aToZ_btn = (Button) view.findViewById(R.id.aToZ_btn);
        zeroTo9_btn = (Button) view.findViewById(R.id.zeroTo9_btn);

        //Numbers keyboard (0-9)
        one_btn = (Button) view.findViewById(R.id.one_btn);
        two_btn = (Button) view.findViewById(R.id.two_btn);
        three_btn = (Button) view.findViewById(R.id.three_btn);
        four_btn = (Button) view.findViewById(R.id.four_btn);
        five_btn = (Button) view.findViewById(R.id.five_btn);
        six_btn = (Button) view.findViewById(R.id.six_btn);
        seven_btn = (Button) view.findViewById(R.id.seven_btn);
        eight_btn = (Button) view.findViewById(R.id.eight_btn);
        nine_btn = (Button) view.findViewById(R.id.nine_btn);
        zero_btn = (Button) view.findViewById(R.id.zero_btn);
        fullstop_btn = (Button) view.findViewById(R.id.fullstop_btn);
        dash_btn = (Button) view.findViewById(R.id.dash_btn);
        leftBracket_btn = (Button) view.findViewById(R.id.leftBracket_btn);
        rightBracket_btn = (Button) view.findViewById(R.id.rightBracket_btn);
        space_btn = (Button) view.findViewById(R.id.space_btn);
        num_backspace_btn = (Button) view.findViewById(R.id.backspace1_btn);

        //show letters keyboard first when fragment is used
        hide_numbers_btn();
        aToZ_btn.setActivated(true);

        //Shows numbers keyboard when (0-9) button pressed
        zeroTo9_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_numbers_keyboard();
            }
        });

        //Shows letters keyboard when (a-z) button is pressed
        aToZ_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_letters_keyboard();
            }
        });

        return view;

    }

    public void show_letters_keyboard() {
        show_letters_btn();
        hide_numbers_btn();
        aToZ_btn.setActivated(true);
        zeroTo9_btn.setActivated(false);
    }

    public void show_numbers_keyboard() {
        show_numbers_btn();
        hide_letters_btn();
        aToZ_btn.setActivated(false);
        zeroTo9_btn.setActivated(true);
    }


    public void show_letters_btn() {
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

    public void hide_letters_btn() {
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


    public void hide_numbers_btn(){
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

    public void show_numbers_btn(){
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
