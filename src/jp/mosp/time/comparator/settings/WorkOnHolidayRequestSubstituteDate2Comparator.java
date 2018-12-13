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
 * 
 */
package jp.mosp.time.comparator.settings;

import java.util.Comparator;

import jp.mosp.time.dto.settings.WorkOnHolidayRequestListDtoInterface;

/**
 *
 */
public class WorkOnHolidayRequestSubstituteDate2Comparator implements Comparator<WorkOnHolidayRequestListDtoInterface> {
	
	@Override
	public int compare(WorkOnHolidayRequestListDtoInterface dto1, WorkOnHolidayRequestListDtoInterface dto2) {
		if (dto1.getSubstituteDate2() != null && dto2.getSubstituteDate2() != null) {
			return dto1.getSubstituteDate2().compareTo(dto2.getSubstituteDate2());
		} else if (dto1.getSubstituteDate2() == null && dto2.getSubstituteDate2() == null) {
			return 0;
		} else if (dto1.getSubstituteDate2() == null) {
			return -1;
		} else {
			return 1;
		}
	}
	
}
