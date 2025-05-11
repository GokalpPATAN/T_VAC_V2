package com.farukayata.t_vac_kotlin.model

import android.os.Parcelable
import com.farukayata.t_vac_kotlin.R
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Tree(
    val name: String = "",
    val temperatureRange: @RawValue IntRange = 0..0,
    val humidityRange: @RawValue IntRange = 0..0,
    val features: String = "",
    val img: Int = R.drawable.ic_default_tree,
    val plantingInfo: String = "",
    val locationCompatibilityNote: String = "",
    val locationNote: String = ""
) : Parcelable {

    fun isSuitable(temperature: Int, humidity: Int): Boolean {
        return temperature in temperatureRange && humidity in humidityRange
    }

    fun nameFilter(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

// Ağaç türleri listesi
/*
var treeList = listOf(
    Tree("Meşe", listOf(15, 30), listOf(40, 70), "Güçlü ve dayanıklıdır, geniş alanlara ihtiyaç duyar.", R.drawable.ic_oak),
    Tree("Çam", listOf(-5, 30), listOf(30, 60), "Soğuk iklimlere de dayanıklıdır, hızlı büyür.", R.drawable.ic_pine),
    Tree("Zeytin", listOf(20, 35), listOf(30, 50), "Sıcak ve kuru iklimleri sever, az su ister.", R.drawable.ic_olive),
    Tree("Kestane", listOf(10, 25), listOf(60, 80), "Nemli ortamları sever, verimli toprakta iyi gelişir.", R.drawable.ic_chestnut),
    Tree("Ceviz", listOf(15, 28), listOf(50, 70), "Derin ve nemli toprakları sever, geniş kök yapısına ihtiyaç duyar.", R.drawable.ic_walnut),
    Tree("Akasya", listOf(15, 35), listOf(20, 40), "Kuraklığa dayanıklıdır, az su gerektirir.", R.drawable.ic_acacia),
    Tree("Kavak", listOf(10, 25), listOf(50, 70), "Su kaynaklarına yakın bölgelerde daha hızlı büyür.", R.drawable.ic_poplar),
    Tree("Ardıç", listOf(5, 25), listOf(30, 50), "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.", R.drawable.ic_juniper),
    Tree("Söğüt", listOf(15, 30), listOf(60, 80), "Su kenarlarında ve nemli topraklarda yetişir, hızla büyür.", R.drawable.ic_willow),
    Tree("Eray", listOf(0, 50), listOf(0, 50), "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.", R.drawable.ic_juniper),
    Tree("Erarslan", listOf(0, 50), listOf(0, 50), "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.", R.drawable.ic_juniper),
)
 */


var treeList = listOf(
    Tree(
        "Meşe",
        15..30,
        40..70,
        "Güçlü ve dayanıklıdır, geniş alanlara ihtiyaç duyar.",
        R.drawable.ic_oak,
        "Meşe tohumları sonbaharda doğrudan toprağa ekilmeli. Nemli ve iyi drene edilmiş toprak seçilmeli. İlk yıl düzenli sulama gerekir.",
        locationNote = "Karadeniz ve Marmara bölgelerinde yaygındır."
    ),
    Tree(
        "Çam",
        -5..30,
        30..60,
        "Soğuk iklimlere de dayanıklıdır, hızlı büyür.",
        R.drawable.ic_pine,
        "Çam fideleri ilkbaharda güneşli alanlara dikilmeli. Toprak nemli tutulmalı, aşırı su kaçınılmalıdır.",
        locationNote = "İç Anadolu ve Doğu Anadolu bölgelerine uygundur."
    ),
    Tree(
        "Zeytin",
        20..35,
        30..50,
        "Sıcak ve kuru iklimleri sever, az su ister.",
        R.drawable.ic_olive,
        "Zeytin fidanları sonbaharda veya erken ilkbaharda dikilmeli. Hafif alkali, iyi drene toprak tercih edilmeli. İlk 2 yıl düzenli sulama yapılmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Kestane",
        10..25,
        60..80,
        "Nemli ortamları sever, verimli toprakta iyi gelişir.",
        R.drawable.ic_chestnut,
        "Kestane fidanları sonbaharda veya erken ilkbaharda dikilmeli. Asidik ve iyi drene toprak tercih edilmeli. Sulama düzenli yapılmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Ceviz",
        15..28,
        50..70,
        "Derin ve nemli toprakları sever, geniş kök yapısına ihtiyaç duyar.",
        R.drawable.ic_walnut,
        "Ceviz fidanları sonbaharda ekilmeli. Derin, humuslu ve nemli topraklar tercih edilmeli. Genç ağaçlarda yaz boyunca sulama önemlidir.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Akasya",
        15..35,
        20..40,
        "Kuraklığa dayanıklıdır, az su gerektirir.",
        R.drawable.ic_acacia,
        "Akasya tohumları ilkbaharda doğrudan toprağa ekilmeli. Su geçirgenliği yüksek toprak tercih edilmeli. Kurak dönemlerde minimal sulama yapılmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Kavak",
        10..25,
        50..70,
        "Su kaynaklarına yakın bölgelerde daha hızlı büyür.",
        R.drawable.ic_poplar,
        "Kavak fideleri erken ilkbaharda dikilmeli. Su kaynaklarına yakın, nemli topraklar seçilmeli. İlk yıl düzenli sulama gereklidir.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Ardıç",
        5..25,
        30..50,
        "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.",
        R.drawable.ic_juniper,
        "Ardıç tohumları sonbaharda ekilmeli. Taşlı ve hafif topraklar tercih edilmeli. Sulama ihtiyacı azdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Söğüt",
        15..30,
        60..80,
        "Su kenarlarında ve nemli topraklarda yetişir, hızla büyür.",
        R.drawable.ic_willow,
        "Söğüt fidanları ilkbaharda ekilmeli. Su kenarları ve nemli alanlar tercih edilmeli. Toprak nemi sürekli korunmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Eray",
        0..50,
        0..50,
        "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.",
        R.drawable.ic_juniper,
        "Eray türü tohumları ilkbaharda dikilmeli. Taşlı ve kurak topraklar tercih edilmeli. Düzenli sulama gerektirmez.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        "Erarslan",
        0..50,
        0..50,
        "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.",
        R.drawable.ic_juniper,
        "Erarslan türü için ilkbaharda dikim yapılmalı. Kuraklığa dayanıklı alanlar tercih edilmeli. Az su ihtiyacı vardır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    )
)


