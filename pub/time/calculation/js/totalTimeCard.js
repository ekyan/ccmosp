/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 譎ょｷｮ蜃ｺ蜍､螟画峩遒ｺ隱阪Γ繝・そ繝ｼ繧ｸ縲・
 */
var MODE_STATE_FIN	= "fin";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	setDisabled("txtOverRestTimeHour", true);
	setDisabled("txtOverRestTimeMinute", true);
	setDisabled("txtRestWorkOnOverHour", true);
	setDisabled("txtRestWorkOnOverMinute", true);
	setDisabled("txtWorkOnOverHour", true);
	setDisabled("txtWorkOnOverMinute", true);
	// 新規登録モード
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		// 登録ボタン押下不可
		setDisabled("btnRegist", true);
	} else {
		// 登録ボタン押下可
		setDisabled("btnRegist", false);
	}
	// 締状態によって制御
	if (MODE_STATE_FIN == jsModeCutoffStateEdit){
		// 更新ボタン押下不可
		// 再表示ボタン押下不可
		setDisabled("btnSelect", true);
		setDisabled("btnReset", true);
	}
}
