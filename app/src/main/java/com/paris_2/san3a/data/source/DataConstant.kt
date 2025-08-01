package com.paris_2.san3a.data.source

import com.paris_2.san3a.domain.entity.Service

object DataConstant {
    val servicesList = listOf(
        Service(
            id = "1",
            title = mapOf("en" to "Plumbing", "ar" to "سباكة"),
            description = mapOf("en" to "Pipes, faucets, water heaters", "ar" to "الأنابيب، الصنابير، سخانات المياه")
        ),
        Service(
            id = "2",
            title = mapOf("en" to "Electrical Services", "ar" to "الخدمات الكهربائية"),
            description = mapOf("en" to "Wiring, installations, repairs", "ar" to "الأسلاك والتركيبات والإصلاحات")
        ),
        Service(
            id = "3",
            title = mapOf("en" to "Cleaning", "ar" to "تنظيف"),
            description = mapOf("en" to "House, office, deep cleaning", "ar" to "المنزل، المكتب، التنظيف العميق")
        ),
        Service(
            id = "4",
            title = mapOf("en" to "AC Repair", "ar" to "إصلاح مكيف الهواء"),
            description = mapOf("en" to "Air conditioning, heating", "ar" to "تكييف الهواء والتدفئة")
        ),
        Service(
            id = "5",
            title = mapOf("en" to "Furniture", "ar" to "أثاث"),
            description = mapOf("en" to "Furniture assembly and repair", "ar" to "تركيب وإصلاح الأثاث")
        ),
        Service(
            id = "6",
            title = mapOf("en" to "Landscaping", "ar" to "تنسيق الحدائق"),
            description = mapOf("en" to "Design, maintenance, gardening", "ar" to "التصميم والصيانة والبستنة")
        ),
        Service(
            id = "7",
            title = mapOf("en" to "Roofing", "ar" to "أسقف"),
            description = mapOf("en" to "Roof installation and repair", "ar" to "تركيب وإصلاح الأسقف")
        ),
        Service(
            id = "8",
            title = mapOf("en" to "Pest Control", "ar" to "مكافحة حشرات"),
            description = mapOf("en" to "Pest elimination services", "ar" to "خدمات القضاء على الحشرات")
        ),
        Service(
            id = "9",
            title = mapOf("en" to "Carpentry", "ar" to "نجارة"),
            description = mapOf("en" to "Woodwork and carpentry services", "ar" to "خدمات النجارة والأعمال الخشبية")
        ),
        Service(
            id = "10",
            title = mapOf("en" to "Appliance Repair", "ar" to "تصليح أجهزة"),
            description = mapOf("en" to "Home appliance repair", "ar" to "تصليح الأجهزة المنزلية")
        ),
        Service(
            id = "11",
            title = mapOf("en" to "Painting", "ar" to "دهان"),
            description = mapOf("en" to "Interior, exterior, touch-ups", "ar" to "الداخلية والخارجية واللمسات النهائية")
        ),
        Service(
            id = "12",
            title = mapOf("en" to "Masonry", "ar" to "بناء"),
            description = mapOf("en" to "Brickwork and masonry services", "ar" to "خدمات البناء والطوب")
        ),
        Service(
            id = "13",
            title = mapOf("en" to "HVAC Maintenance", "ar" to "صيانة تكييف"),
            description = mapOf("en" to "Heating and cooling system maintenance", "ar" to "صيانة أنظمة التدفئة والتبريد")
        ),
        Service(
            id = "14",
            title = mapOf("en" to "Pool Maintenance", "ar" to "صيانة مسابح"),
            description = mapOf("en" to "Swimming pool cleaning and maintenance", "ar" to "تنظيف وصيانة المسابح")
        )
    )
}