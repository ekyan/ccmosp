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
 * ポータル画面用日付曜日時刻表示
 */
function time(){
	var now = new Date();
	var weeks = new Array("日","月","火","水","木","金","土") ;

	var year = now.getFullYear() ;
	var month = now.getMonth() + 1 ;
	var date = now.getDate() ;
	var week = weeks[now.getDay()] ;
	
	var hour = now.getHours(); // 時
	var min = now.getMinutes(); // 分
	var sec = now.getSeconds(); // 秒

	// 数値が1桁の場合、頭に0を付けて2桁で表示する指定
	if(hour < 10) { hour = "0" + hour; }
	if(min < 10) { min = "0" + min; }
	if(sec < 10) { sec = "0" + sec; }

	// フォーマットを指定（不要な行を削除する）
	var watch1 = year + '年' + month + '月' + date + '日（' + week + '）' + hour + ':' + min + ':' + sec;

	// テキストフィールドにデータを渡す処理（不要な行を削除する）
	document.form.clock.value = watch1;

	setTimeout("time()", 1000);
}
