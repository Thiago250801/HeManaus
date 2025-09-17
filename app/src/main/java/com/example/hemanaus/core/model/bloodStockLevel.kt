package com.example.hemanaus.core.model


fun bloodStockLevel(): List<BloodStock> = listOf(
    BloodStock("A+", 15, StockStatus.CRITICAL),
    BloodStock("O-", 8, StockStatus.CRITICAL),
    BloodStock("B+", 25, StockStatus.LOW),
    BloodStock("AB-", 5, StockStatus.CRITICAL)

)