package com.riyas.creacevijay.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase:RoomDatabase() {
    abstract val productDaoInProductDb:ProductDao

    companion object{
        @Volatile
        private var INSTANCE:ProductDatabase?=null

        fun getInstance(context: Context):ProductDatabase{
            synchronized(this){
                var instance= INSTANCE
                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_database_db"
                    ).build()
                }
                return instance
            }
        }
    }

}