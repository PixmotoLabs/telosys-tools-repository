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
package org.telosys.tools.repository.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.telosys.tools.commons.StrUtil;
import org.telosys.tools.generic.model.Entity;
import org.telosys.tools.generic.model.Model;
import org.telosys.tools.generic.model.ModelType;

public class RepositoryModel implements Model
{
	private String name = "" ;
	
	private String description = "" ;
	
	private String databaseName ;

	private int    databaseId = -1 ; // Database Id in the ".dbcfg" file ( v 2.1.0 )

	private String databaseProductName ; 
	
	private Date   generationDate ;
	
	private Date   lastUpdateDate ;
	
	private Hashtable<String,EntityInDbModel> htEntities = new Hashtable<String,EntityInDbModel>() ; 

	//--------------------------------------------------------------------------------------
	@Override
	public ModelType getType() {
		return ModelType.DATABASE_SCHEMA ;
	}

	//--------------------------------------------------------------------------------------
	@Override
	public String getVersion() {
		return DbModelVersion.VERSION;
	}

	//--------------------------------------------------------------------------------------
	@Override
	public String getName() {
		// Cannot be null or void
		if ( StrUtil.nullOrVoid(name) ) {
			return "Db#" + databaseId ;
		}
		else {
			return name ;
		}
	}
	
	public void setName( String name) {
		this.name = name;
	}

	//--------------------------------------------------------------------------------------
	@Override
	public String getDescription() {
		return description ;
	}

	public void setDescription(String description) {
		this.description = description ;
	}

	//--------------------------------------------------------------------------------------
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	//--------------------------------------------------------------------------------------
	@Override
	public Integer getDatabaseId() {
		return databaseId;
	}

	/**
	 * Returns the database id ( ".dbcfg" id )
	 * @return
	 */
	public int getDatabaseIdAsInt() {
		return databaseId;
	}

	/**
	 * Set the database id ( ".dbcfg" id )
	 * @param databaseId
	 */
	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}

	//--------------------------------------------------------------------------------------
	@Override
	public String getDatabaseProductName() {
		return databaseProductName;
	}

	/**
	 * Set the database product name retrieved from the meta-data
	 * @param databaseType
	 */
	public void setDatabaseProductName(String databaseType) {
		this.databaseProductName = databaseType;
	}

	//--------------------------------------------------------------------------------------
	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}

	//--------------------------------------------------------------------------------------
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	//-------------------------------------------------------------------------------
	// ENTITIES management
	//-------------------------------------------------------------------------------
	
	public int getNumberOfEntities() {
		return htEntities.size();
	}

	/**
	 * Returns an array of all the entities of the model.<br>
	 * The entities are sorted by name.
	 * 
	 * @return
	 */
	public EntityInDbModel[] getEntitiesArray() {
		EntityInDbModel[] array = (EntityInDbModel[]) htEntities.values().toArray( new EntityInDbModel[htEntities.size()] );
		Arrays.sort(array);
		return array ;
	}
	
//	/**
//	 * Returns a collection of all the entities of the model.<br>
//	 * The entities are sorted by name.
//	 * 
//	 * @return
//	 */
//	public Collection<EntityInDbModel> getEntitiesCollection() {
//		//return htEntities.values() ;
//		EntityInDbModel[] entities = getEntitiesArray();
//		return Arrays.asList(entities);
//	}

	@Override
	public List<Entity> getEntities() {
		EntityInDbModel[] entities = getEntitiesArray();
		LinkedList<Entity> list = new LinkedList<Entity>();
		for ( Entity entity : entities ) {
			list.add(entity);
		}
		return list ;
	}
	
//	/**
//	 * Returns the entity for the given name, or null if not found
//	 * @param name
//	 * @return
//	 */
//	public EntityInDbModel getEntityByName(String name) {
//		return htEntities.get(name);
//	}
	
	@Override
	public EntityInDbModel getEntityByTableName(String entityTableName) {
		return htEntities.get(entityTableName);
	}

	@Override
	public EntityInDbModel getEntityByClassName(String entityClassName) {
		for ( EntityInDbModel entity : htEntities.values() ) {
			if ( entity.getClassName().equals(entityClassName) ) {
				return entity ; // Found
			}
		}
		return null; // Not found
	}

	/**
	 * Returns an array containing the table names for all the entities stored in the model.<br>
	 * The names are sorted in alphabetic order.
	 * 
	 * @return
	 */
	public String[] getEntitiesNames() {
		Collection<EntityInDbModel> values = htEntities.values();
		String[] names = new String[values.size()];
		int cpt = 0;
		for ( EntityInDbModel entity : values ) {
			names[cpt] = entity.getDatabaseTable();
			cpt++;
		}
		Arrays.sort(names);
		return names;
	}
	
	public void storeEntity(EntityInDbModel entity) {
//		htEntities.put(entity.getName(), entity);
		htEntities.put(entity.getDatabaseTable(), entity); // v 3.0.0
	}
	
	/**
	 * Removes the entity having the given table name (if any)
	 * @param entityTableName
	 * @return the entity removed (or null if none)
	 */
	public EntityInDbModel removeEntity(String entityTableName) {
		return htEntities.remove(entityTableName);
	}
	
	//-------------------------------------------------------------------------------
	// LINKS management
	//-------------------------------------------------------------------------------
	/**
	 * Returns the link for the given id
	 * @param id
	 * @return
	 */
	public LinkInDbModel getLinkById(String id) {
		if ( id != null ) {
			EntityInDbModel [] entities = this.getEntitiesArray();
			for ( int i = 0 ; i < entities.length ; i++ ) {
				EntityInDbModel entity = entities[i];
//				LinkInDbModel [] links = entity.getLinks();
				LinkInDbModel [] links = entity.getLinksArray();
				for ( int j = 0 ; j < links.length ; j++ ) {
					LinkInDbModel link = links[j];
					if ( id.equals( link.getId() ) )  {
						return link;
					}
				}
			}
		}
		return null ;
	}

	//-------------------------------------------------------------------------------
	/**
	 * Removes all the links in the model (for all the entities)
	 */
	public void removeAllLinks() {
		EntityInDbModel [] entities = this.getEntitiesArray();
		for ( int i = 0 ; i < entities.length ; i++ ) {
			EntityInDbModel entity = entities[i];
			entity.removeAllLinks();
		}
	}
	
	//-------------------------------------------------------------------------------
	/**
	 * Removes the link corresponding to the given id
	 * @param id
	 * @return 1 if the link has been found and removed, 0 if the link has not been found
	 */
	public int removeLinkById(String id) {
		int count = 0 ;
		LinkInDbModel link = getLinkById(id);
		if ( link != null ) {
			//--- Remove link 
//			EntityInDbModel entity = getEntityByName( link.getSourceTableName() );
			EntityInDbModel entity = getEntityByTableName( link.getSourceTableName() );
			if ( entity != null ) {
				count = entity.removeLink(link);
			}
		}
		return count ;
	}
	
	/**
	 * Removes all the links using the given entity name (as source entity or target entity)
	 * @param entityName
	 * @return the number of links removed
	 * @since 2.1.1
	 */
	public int removeLinksByEntityName(String entityName) {
		int count = 0 ;
		for ( EntityInDbModel entity : this.getEntitiesArray() ) {
//			for ( LinkInDbModel link : entity.getLinks() ) {
			for ( LinkInDbModel link : entity.getLinksArray() ) {
				if ( entityName.equals( link.getSourceTableName() ) || entityName.equals( link.getTargetTableName() ) ) {
					count = count + entity.removeLink(link);
				}
			}
		}
		return count ;
	}
	
	/**
	 * Removes the links based on the given Foreign Key
	 * @param foreignKey
	 * @return the number of links removes (usually 2)
	 */
	public int removeLinksByForeignKey(ForeignKeyInDbModel foreignKey) {
		int count = 0 ;
		//--- Build the 2 link id
		String owningSideLinkId  = LinkInDbModel.buildId(foreignKey, true) ;
		String inverseSideLinkId = LinkInDbModel.buildId(foreignKey, false) ;
		//--- Remove the links if they are already in the model
		count = count + this.removeLinkById(inverseSideLinkId);
		count = count + this.removeLinkById(owningSideLinkId);

		return count ;
	}
	
	//-------------------------------------------------------------------------------
	/**
	 * Removes all the links built on the given "join table" name <br>
	 * Each "join table" is supposed to have 2 links in the model (or 0 if not used)
	 * @param joinTableName
	 * @return the number of links deleted (0 or 2 expected)
	 * @since 2.1.1
	 */
	public int removeLinksByJoinTableName(String joinTableName) {
		int count = 0 ;
		if ( joinTableName != null ) {
			for ( EntityInDbModel entity : this.getEntitiesArray() ) {
//				for ( LinkInDbModel link : entity.getLinks() ) {
				for ( LinkInDbModel link : entity.getLinksArray() ) {
					String jtName = link.getJoinTableName() ;
					if ( jtName != null ) {
						if ( jtName.equals(joinTableName) ) {
							entity.removeLink(link);
							count++;
						}
					}
				}
			}
		}
		return count ;
	}
	//-------------------------------------------------------------------------------
	/**
	 * Returns the RelationLinks ( the 2 links of a relation ) for the given link id
	 * @param linkId the id of one of the 2 links of the relation
	 * @return
	 */
	public RelationLinksInDbModel getRelationByLinkId(String linkId) 
	{
		LinkInDbModel link1 = getLinkById(linkId);
		if ( link1 != null ) {
			if ( link1.isOwningSide() ) {
				//--- Owning Side => try to found the inverse side
				EntityInDbModel [] entities = this.getEntitiesArray();
				for ( int i = 0 ; i < entities.length ; i++ ) {
					EntityInDbModel entity = entities[i];
//					LinkInDbModel [] links = entity.getLinks();
					LinkInDbModel [] links = entity.getLinksArray();
					for ( int j = 0 ; j < links.length ; j++ ) {
						LinkInDbModel link2 = links[j];
						if ( link2.isOwningSide() == false ) {
							//if ( linkId.equals( link2.getInverseSideOf() ) ) {
							if ( linkId.equals( link2.getInverseSideLinkId() ) ) { // v 3.0.0
								return new RelationLinksInDbModel ( link1, link2 );
							}
						}
					}
				}
				// inverse side not found 
				return new RelationLinksInDbModel ( link1, null );
			}
			else {
				//--- Inverse Side => try to found the owning side
				//LinkInDbModel link2 = getLinkById( link1.getInverseSideOf() ) ;
				LinkInDbModel link2 = getLinkById( link1.getInverseSideLinkId() ) ; // v 3.0.0
				return new RelationLinksInDbModel ( link2, link1 );
			}
		}
		return null ;
	}
	
	//-------------------------------------------------------------------------------
	// FOREIGN KEYS management
	//-------------------------------------------------------------------------------
	/**
	 * Search and return a Foreign Key  
	 * @param fkName the name to e searched
	 * @return the Foreign Key or null if not found
	 */
	public ForeignKeyInDbModel getForeignKeyByName(String fkName)
	{
		EntityInDbModel [] entities = this.getEntitiesArray();
		for ( EntityInDbModel entity : entities ) {
			ForeignKeyInDbModel fk = entity.getForeignKey(fkName);
			if ( fk != null ) {
				return fk ; // FOUND 
			}
		}
		return null ;
	}

}
