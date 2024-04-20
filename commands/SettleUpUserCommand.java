package com.scaler.splitwise.commands;

import com.scaler.splitwise.controllers.DTOs.SettleUpUserRequestDTO;
import com.scaler.splitwise.controllers.DTOs.SettleUpUserResponseDTO;
import com.scaler.splitwise.controllers.SettleUpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpUserCommand implements Command{

    private SettleUpController settleUpController;

    @Autowired
    public SettleUpUserCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    @Override
    public boolean matches(String input) {
        String[] words = input.split(" ");
        if(words[0].equals("settleUpUser")){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        //input: SettleupUser uderId;
        String[] words = input.split(" ");
        Long userId = Long.valueOf(words[1]);

        SettleUpUserRequestDTO requestDTO = new SettleUpUserRequestDTO();
        requestDTO.setUserId(userId);
        SettleUpUserResponseDTO responseDTO  = settleUpController.settleUpUser(requestDTO);
        System.out.println(responseDTO.getResponseStatus());
        System.out.println(responseDTO.getSettleUpTransactions());
    }
}
