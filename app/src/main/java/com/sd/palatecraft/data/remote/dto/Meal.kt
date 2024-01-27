package com.sd.palatecraft.data.remote.dto

import com.sd.palatecraft.data.local.MealEntitiy
private  val enc: String = ""
data class Meal(
    val dateModified: String,
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strCreativeCommonsConfirmed: String,
    val strDrinkAlternate: String,
    val strImageSource: String,
    val strIngredient1: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strIngredient16: String,
    val strIngredient17: String,
    val strIngredient18: String,
    val strIngredient19: String,
    val strIngredient2: String,
    val strIngredient20: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val strMeasure1: String,
    val strMeasure10: String,
    val strMeasure11: String,
    val strMeasure12: String,
    val strMeasure13: String,
    val strMeasure14: String,
    val strMeasure15: String,
    val strMeasure16: String,
    val strMeasure17: String,
    val strMeasure18: String,
    val strMeasure19: String,
    val strMeasure2: String,
    val strMeasure20: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: String,
    val strMeasure6: String,
    val strMeasure7: String,
    val strMeasure8: String,
    val strMeasure9: String,
    val strSource: String,
    val strTags: String,
    val strYoutube: String
){
    fun toMealEntity(): MealEntitiy{
        return MealEntitiy(
            dateModified = dateModified.plus(enc),
            idMeal = idMeal.plus(enc),
            strArea = strArea.plus(enc),
            strCategory = strCategory.plus(enc),
            strCreativeCommonsConfirmed = strCreativeCommonsConfirmed.plus(enc),
            strDrinkAlternate = strDrinkAlternate.plus(enc),
            strImageSource = strImageSource.plus(enc),
            strIngredient1 = strIngredient1.plus(enc),
            strIngredient10 = strIngredient10.plus(enc),
            strIngredient11 = strIngredient11.plus(enc),
            strIngredient12 = strIngredient12.plus(enc),
            strIngredient13 = strIngredient13.plus(enc),
            strIngredient14 = strIngredient14.plus(enc),
            strIngredient15 = strIngredient15.plus(enc),
            strIngredient16 = strIngredient16.plus(enc),
            strIngredient17 = strIngredient17.plus(enc),
            strIngredient18 = strIngredient18.plus(enc),
            strIngredient19 = strIngredient19.plus(enc),
            strIngredient2 = strIngredient2.plus(enc),
            strIngredient20 = strIngredient20.plus(enc),
            strIngredient3 = strIngredient3.plus(enc),
            strIngredient4 = strIngredient4.plus(enc),
            strIngredient5 = strIngredient5.plus(enc),
            strIngredient6 = strIngredient6.plus(enc),
            strIngredient7 = strIngredient7.plus(enc),
            strIngredient8 = strIngredient8.plus(enc),
            strIngredient9 = strIngredient9.plus(enc),
            strInstructions = strInstructions.plus(enc),
            strMeal = strMeal.plus(enc),
            strMealThumb = strMealThumb.plus(enc),
            strMeasure1 = strMeasure1.plus(enc),
            strMeasure10 = strMeasure10.plus(enc),
            strMeasure11 = strMeasure11.plus(enc),
            strMeasure12 = strMeasure12.plus(enc),
            strMeasure13 = strMeasure13.plus(enc),
            strMeasure14 = strMeasure14.plus(enc),
            strMeasure15 = strMeasure15.plus(enc),
            strMeasure16 = strMeasure16.plus(enc),
            strMeasure17 = strMeasure17.plus(enc),
            strMeasure18 = strMeasure18.plus(enc),
            strMeasure19 = strMeasure19.plus(enc),
            strMeasure2 = strMeasure2.plus(enc),
            strMeasure20 = strMeasure20.plus(enc),
            strMeasure3 = strMeasure3.plus(enc),
            strMeasure4 = strMeasure4.plus(enc),
            strMeasure5 = strMeasure5.plus(enc),
            strMeasure6 = strMeasure6.plus(enc),
            strMeasure7 = strMeasure7.plus(enc),
            strMeasure8 = strMeasure8.plus(enc),
            strMeasure9 = strMeasure9.plus(enc),
            strSource = strSource.plus(enc),
            strTags = strTags.plus(enc),
            strYoutube = strYoutube.plus(enc)

        )
    }
}