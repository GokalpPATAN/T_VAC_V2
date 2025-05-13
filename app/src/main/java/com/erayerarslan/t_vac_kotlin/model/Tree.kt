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
    val locationNote: String = "",
    val imageUrl: String? = null
) : Parcelable {

    fun isSuitable(temperature: Int, humidity: Int): Boolean {
        return temperature in temperatureRange && humidity in humidityRange
    }

    fun nameFilter(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

var treeList = listOf(
    Tree(
        name = "Meşe",
        temperatureRange = 15..30,
        humidityRange = 40..70,
        features = "Güçlü ve dayanıklıdır, geniş alanlara ihtiyaç duyar.",
        img = R.drawable.ic_oak,
        plantingInfo = "Meşe tohumları sonbaharda doğrudan toprağa ekilmeli. Nemli ve iyi drene edilmiş toprak seçilmeli. İlk yıl düzenli sulama gerekir.",
        locationNote = "Karadeniz ve Marmara bölgelerinde yaygındır."
    ),
    Tree(
        name = "Çam",
        temperatureRange = -5..30,
        humidityRange = 30..60,
        features = "Soğuk iklimlere de dayanıklıdır, hızlı büyür.",
        img = R.drawable.ic_pine,
        plantingInfo = "Çam fideleri ilkbaharda güneşli alanlara dikilmeli. Toprak nemli tutulmalı, aşırı su kaçınılmalıdır.",
        locationNote = "İç Anadolu ve Doğu Anadolu bölgelerine uygundur."
    ),
    Tree(
        name = "Zeytin",
        temperatureRange = 20..35,
        humidityRange = 30..50,
        features = "Sıcak ve kuru iklimleri sever, az su ister.",
        img = R.drawable.ic_olive,
        plantingInfo = "Zeytin fidanları sonbaharda veya erken ilkbaharda dikilmeli. Hafif alkali, iyi drene toprak tercih edilmeli. İlk 2 yıl düzenli sulama yapılmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Kestane",
        temperatureRange = 10..25,
        humidityRange = 60..80,
        features = "Nemli ortamları sever, verimli toprakta iyi gelişir.",
        img = R.drawable.ic_chestnut,
        plantingInfo = "Kestane fidanları sonbaharda veya erken ilkbaharda dikilmeli. Asidik ve iyi drene toprak tercih edilmeli. Sulama düzenli yapılmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Ceviz",
        temperatureRange = 15..28,
        humidityRange = 50..70,
        features = "Derin ve nemli toprakları sever, geniş kök yapısına ihtiyaç duyar.",
        img = R.drawable.ic_walnut,
        plantingInfo = "Ceviz fidanları sonbaharda ekilmeli. Derin, humuslu ve nemli topraklar tercih edilmeli. Genç ağaçlarda yaz boyunca sulama önemlidir.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Akasya",
        temperatureRange = 15..35,
        humidityRange = 20..40,
        features = "Kuraklığa dayanıklıdır, az su gerektirir.",
        img = R.drawable.ic_acacia,
        plantingInfo = "Akasya tohumları ilkbaharda doğrudan toprağa ekilmeli. Su geçirgenliği yüksek toprak tercih edilmeli. Kurak dönemlerde minimal sulama yapılmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Kavak",
        temperatureRange = 10..25,
        humidityRange = 50..70,
        features = "Su kaynaklarına yakın bölgelerde daha hızlı büyür.",
        img = R.drawable.ic_poplar,
        plantingInfo = "Kavak fideleri erken ilkbaharda dikilmeli. Su kaynaklarına yakın, nemli topraklar seçilmeli. İlk yıl düzenli sulama gereklidir.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Ardıç",
        temperatureRange = 5..25,
        humidityRange = 30..50,
        features = "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.",
        img = R.drawable.ic_juniper,
        plantingInfo = "Ardıç tohumları sonbaharda ekilmeli. Taşlı ve hafif topraklar tercih edilmeli. Sulama ihtiyacı azdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Söğüt",
        temperatureRange = 15..30,
        humidityRange = 60..80,
        features = "Su kenarlarında ve nemli topraklarda yetişir, hızla büyür.",
        img = R.drawable.ic_willow,
        plantingInfo = "Söğüt fidanları ilkbaharda ekilmeli. Su kenarları ve nemli alanlar tercih edilmeli. Toprak nemi sürekli korunmalıdır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Eray",
        temperatureRange = 0..50,
        humidityRange = 0..50,
        features = "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.",
        img = R.drawable.ic_juniper,
        plantingInfo = "Eray türü tohumları ilkbaharda dikilmeli. Taşlı ve kurak topraklar tercih edilmeli. Düzenli sulama gerektirmez.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    ),
    Tree(
        name = "Erarslan",
        temperatureRange = 0..50,
        humidityRange = 0..50,
        features = "Soğuk ve kurak iklimlerde dayanıklıdır, dağlık alanlarda iyi yetişir.",
        img = R.drawable.ic_juniper,
        plantingInfo = "Erarslan türü için ilkbaharda dikim yapılmalı. Kuraklığa dayanıklı alanlar tercih edilmeli. Az su ihtiyacı vardır.",
        locationNote = "Ege ve Akdeniz bölgelerinde yaygındır."
    )
)
