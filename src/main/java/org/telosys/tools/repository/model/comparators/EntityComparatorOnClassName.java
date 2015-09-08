/**
 *  Copyright (C) 2008-2015  Telosys project org. ( http://www.telosys.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.telosys.tools.repository.model.comparators;

import java.util.Comparator;

import org.telosys.tools.repository.model.EntityInDbModel;

public class EntityComparatorOnClassName implements Comparator<EntityInDbModel>{

	@Override
	public int compare(EntityInDbModel entity1, EntityInDbModel entity2) {
		String className1 = entity1.getClassName() ;
		String className2 = entity2.getClassName();
		if ( className1 != null && className2 != null ) {
			return className1.compareTo(className2);
		}
		return 0;
	}
}
