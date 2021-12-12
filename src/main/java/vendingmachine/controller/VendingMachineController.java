package vendingmachine.controller;

import static vendingmachine.domain.Merchandise.*;
import static vendingmachine.domain.VendingMachine.*;

import java.util.LinkedHashMap;

import vendingmachine.domain.Coin;
import vendingmachine.domain.Merchandise;
import vendingmachine.domain.Merchandises;
import vendingmachine.domain.Money;
import vendingmachine.domain.User;
import vendingmachine.domain.VendingMachine;
import vendingmachine.utils.ErrorMessage;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class VendingMachineController {
	private VendingMachine vendingMachine;
	private User user;

	public void play() {
		vendingMahchineMoneyWithErrorHandling();
		OutputView.showVendingMahcineCoinStatus(castingCoinToInteger(vendingMachine.saveCoinStatus()));
		inputMerchandiseInformationWithErrorHandling();
		inputMoneyWithErrorHandling();
		while(isUserBuyMerchandise(user, vendingMachine.getMerchandises()) && !vendingMachine.getMerchandises().isAllMerchandisesSoldout()){
			OutputView.showInputMoneyStatus(user.getUserMoney().getMoney());
			buyMerchandiseWithErrorHandling();
		}
		OutputView.showChangeMoneyStatus(user.getUserMoney().getMoney(), castingCoinToInteger(vendingMachine.changeCoinStatus(user.getUserMoney())));
	}

	public LinkedHashMap<Integer, Integer> castingCoinToInteger(LinkedHashMap<Coin, Integer> coinStatus) {
		LinkedHashMap<Integer, Integer> intCoinStatus = new LinkedHashMap<>();
		for (Coin coin : coinStatus.keySet()) {
			intCoinStatus.put(coin.getAmount(), coinStatus.get(coin));
		}
		return intCoinStatus;
	}

	public void vendingMahchineMoneyWithErrorHandling() {
		try {
			Money vendingMachineMoney = new Money(Integer.parseInt(InputView.inputVendingMachineMoney()));
			vendingMachine = new VendingMachine(vendingMachineMoney);
		} catch (NumberFormatException numberFormatException) {
			System.out.println(ErrorMessage.INVALID_MONEY_TYPE_ERROR_MESSAGE);
			vendingMahchineMoneyWithErrorHandling();
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(illegalArgumentException.getMessage());
			vendingMahchineMoneyWithErrorHandling();
		}
	}

	public void inputMoneyWithErrorHandling() {
		try {
			user = new User(new Money(Integer.parseInt(InputView.inputMoney())));
		} catch (NumberFormatException numberFormatException) {
			System.out.println(ErrorMessage.INVALID_MONEY_TYPE_ERROR_MESSAGE);
			inputMoneyWithErrorHandling();
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(illegalArgumentException.getMessage());
			inputMoneyWithErrorHandling();
		}
	}

	public void inputMerchandiseInformationWithErrorHandling() {
		try {
			vendingMachine.stockMerchandises(
				new Merchandises(constructMerchandises(InputView.inputMerchandiseInformation())));
		} catch (NumberFormatException numberFormatException) {
			System.out.println(ErrorMessage.INVALID_MONEY_TYPE_ERROR_MESSAGE);
			inputMerchandiseInformationWithErrorHandling();
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(illegalArgumentException.getMessage());
			inputMerchandiseInformationWithErrorHandling();
		}
	}

	public void buyMerchandiseWithErrorHandling() {
		try {
			user.buyMerchandise(InputView.inputMerchandiseName(), vendingMachine.getMerchandises());
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(user.getUserMoney().getMoney());
			System.out.println(illegalArgumentException.getMessage());
			buyMerchandiseWithErrorHandling();
		}
	}
}
