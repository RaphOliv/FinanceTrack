package com.hacksprint.financetrack.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.hacksprint.financetrack.data.CategoryDao
import com.hacksprint.financetrack.data.CategoryEntity
import com.hacksprint.financetrack.data.ExpenseDao
import com.hacksprint.financetrack.data.ExpenseEntity

@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 4)
abstract class FinanceTrackDataBase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getExpenseDao(): ExpenseDao

}