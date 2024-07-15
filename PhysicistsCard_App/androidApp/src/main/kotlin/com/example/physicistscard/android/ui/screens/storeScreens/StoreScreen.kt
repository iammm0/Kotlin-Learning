package com.example.physicistscard.android.ui.screens.storeScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.physicistscard.android.R
import com.example.physicistscard.android.ui.components.layouts.StoreTopAppBar
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.Product
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.ScrollableTapRow

val physicistsProducts = listOf(
    Product(id = "1", name = "艾萨克·牛顿", category = "物理学家", imageUrl = R.drawable.newton, description = "经典物理学的奠基人之一，提出了万有引力定律。", price = "¥6"),
    Product(id = "2", name = "阿尔伯特·爱因斯坦", category = "物理学家", imageUrl = R.drawable.albert, description = "相对论的创立者，对现代物理学产生了深远影响。", price = "¥6"),
    Product(id = "3", name = "詹姆斯·克拉克·麦克斯韦", category = "物理学家", imageUrl = R.drawable.maxwell, description = "电磁理论的奠基人，提出了麦克斯韦方程组。", price = "¥6"),
    Product(id = "4", name = "尼尔斯·玻尔", category = "物理学家", imageUrl = R.drawable.bohr, description = "原子结构和量子力学的先驱。", price = "¥6"),
    Product(id = "5", name = "伽利略·伽利雷", category = "物理学家", imageUrl = R.drawable.galileo, description = "通过观测和实验奠定了现代科学的方法论基础。", price = "¥6"),
    Product(id = "6", name = "亚里士多德", category = "物理学家", imageUrl = R.drawable.aristotle, description = "古希腊哲学家，对物理学的研究影响深远。", price = "¥6"),
    Product(id = "7", name = "约翰·伯努利", category = "物理学家", imageUrl = R.drawable.bernoully, description = "著名数学家，对流体力学和数学分析有重要贡献。", price = "¥6"),
    Product(id = "8", name = "罗伯特·波义耳", category = "物理学家", imageUrl = R.drawable.boyle, description = "现代化学的奠基人之一，提出了波义耳定律。", price = "¥6"),
    Product(id = "9", name = "皮埃尔·居里", category = "物理学家", imageUrl = R.drawable.curie, description = "居里夫妇之一，对放射性研究做出了卓越贡献。", price = "¥6"),
    Product(id = "10", name = "让·勒朗·达朗贝尔", category = "物理学家", imageUrl = R.drawable.dalembert, description = "法国数学家，对力学和流体力学有重要贡献。", price = "¥6"),
    Product(id = "11", name = "约翰·道尔顿", category = "物理学家", imageUrl = R.drawable.dalton, description = "原子论的提出者之一，对化学和物理学有重要贡献。", price = "¥6"),
    Product(id = "12", name = "德谟克里特", category = "物理学家", imageUrl = R.drawable.democritus, description = "古希腊哲学家，提出了原子论。", price = "¥6"),
    Product(id = "13", name = "恩培多克勒", category = "物理学家", imageUrl = R.drawable.empedocles, description = "古希腊哲学家，提出了四元素说。", price = "¥6"),
    Product(id = "14", name = "欧仁·维格纳", category = "物理学家", imageUrl = R.drawable.eugene, description = "量子力学和粒子物理学的杰出贡献者。", price = "¥6"),
    Product(id = "15", name = "莱昂哈德·欧拉", category = "物理学家", imageUrl = R.drawable.euler, description = "瑞士数学家，对数学和物理学的多个领域有重要贡献。", price = "¥6"),
    Product(id = "16", name = "迈克尔·法拉第", category = "物理学家", imageUrl = R.drawable.farberlog, description = "电磁学的奠基人之一，发现了电磁感应现象。", price = "¥6"),
    Product(id = "17", name = "约瑟夫·傅里叶", category = "物理学家", imageUrl = R.drawable.fourier, description = "提出了傅里叶变换，对热传导研究有重要贡献。", price = "¥6"),
    Product(id = "18", name = "莱昂·傅科", category = "物理学家", imageUrl = R.drawable.foucault, description = "著名的傅科摆实验，证明了地球的自转。", price = "¥6"),
    Product(id = "19", name = "卡尔·弗里德里希·高斯", category = "物理学家", imageUrl = R.drawable.gauss, description = "数学和物理学的多面手，提出了高斯定理。", price = "¥6"),
    Product(id = "20", name = "乔治·欧姆", category = "物理学家", imageUrl = R.drawable.george, description = "发现了欧姆定律，对电学研究有重要贡献。", price = "¥6"),
    Product(id = "21", name = "赫拉克利特", category = "物理学家", imageUrl = R.drawable.heraclitus, description = "古希腊哲学家，提出了万物流变的思想。", price = "¥6"),
    Product(id = "22", name = "维尔纳·海森堡", category = "物理学家", imageUrl = R.drawable.heysenberg, description = "量子力学的奠基人之一，提出了测不准原理。", price = "¥6"),
    Product(id = "23", name = "惠更斯", category = "物理学家", imageUrl = R.drawable.huggens, description = "光的波动说的提出者之一，对光学研究有重要贡献。", price = "¥6"),
    Product(id = "24", name = "约翰尼斯·开普勒", category = "物理学家", imageUrl = R.drawable.kepler, description = "行星运动定律的提出者，对天文学研究有重要贡献。", price = "¥6"),
    Product(id = "25", name = "开尔文", category = "物理学家", imageUrl = R.drawable.kervin, description = "热力学的奠基人之一，提出了开尔文温标。", price = "¥6"),
    Product(id = "26", name = "尼古拉·哥白尼", category = "物理学家", imageUrl = R.drawable.kopernik, description = "日心说的提出者，改变了人类对宇宙的认识。", price = "¥6"),
    Product(id = "27", name = "约瑟夫·拉格朗日", category = "物理学家", imageUrl = R.drawable.lagrange, description = "分析力学的奠基人之一，对数学和物理学有重要贡献。", price = "¥6"),
    Product(id = "28", name = "皮埃尔-西蒙·拉普拉斯", category = "物理学家", imageUrl = R.drawable.laplace, description = "天体力学的奠基人之一，提出了拉普拉斯变换。", price = "¥6"),
    Product(id = "29", name = "爱德华·洛伦兹", category = "物理学家", imageUrl = R.drawable.lorenz, description = "混沌理论的奠基人之一，提出了蝴蝶效应。", price = "¥6"),
    Product(id = "30", name = "冯·诺依曼", category = "物理学家", imageUrl = R.drawable.neumann, description = "计算机科学的奠基人之一，提出了存储程序概念。", price = "¥6"),
    Product(id = "31", name = "伊多克斯", category = "物理学家", imageUrl = R.drawable.odoxus, description = "古希腊天文学家，对几何学和天文学有重要贡献。", price = "¥6"),
    Product(id = "33", name = "罗伯特·奥本海默", category = "物理学家", imageUrl = R.drawable.oppheimer, description = "曼哈顿计划的领导者之一，被誉为原子弹之父。", price = "¥6"),
    Product(id = "34", name = "沃尔夫冈·泡利", category = "物理学家", imageUrl = R.drawable.pauli, description = "提出了泡利不相容原理，对量子力学有重要贡献。", price = "¥6"),
    Product(id = "35", name = "皮埃尔·西蒙·拉普拉斯", category = "物理学家", imageUrl = R.drawable.pierre, description = "提出了拉普拉斯变换，对天体力学研究有重要贡献。", price = "¥6"),
    Product(id = "36", name = "柏拉图", category = "物理学家", imageUrl = R.drawable.plato, description = "古希腊哲学家，对西方哲学和科学有深远影响。", price = "¥6"),
    Product(id = "37", name = "亨利·庞加莱", category = "物理学家", imageUrl = R.drawable.poincare, description = "拓扑学和混沌理论的先驱，对数学和物理学有重要贡献。", price = "¥6"),
    Product(id = "38", name = "毕达哥拉斯", category = "物理学家", imageUrl = R.drawable.pythagoras, description = "古希腊数学家，提出了著名的毕达哥拉斯定理。", price = "¥6"),
    Product(id = "39", name = "泰勒斯", category = "物理学家", imageUrl = R.drawable.tailles, description = "古希腊哲学家，被誉为自然哲学之父。", price = "¥6"),

    Product(id = "40", name = "相对论", category = "理论学说", imageUrl = R.drawable.relativity, description = "由阿尔伯特·爱因斯坦提出的理论，改变了我们对时间和空间的理解。", price = "¥6"),
    Product(id = "41", name = "量子力学", category = "理论学说", imageUrl = R.drawable.quantum_mechanics, description = "描述微观粒子行为的理论，是现代物理学的重要基石。", price = "¥6"),
    Product(id = "42", name = "电磁理论", category = "理论学说", imageUrl = R.drawable.electromagnetic_theory, description = "由詹姆斯·克拉克·麦克斯韦提出，描述了电磁场的行为。", price = "¥6"),
    Product(id = "43", name = "热力学", category = "理论学说", imageUrl = R.drawable.thermodynamics, description = "研究能量转换和物质性质的理论，包括著名的热力学定律。", price = "¥6"),

    Product(id = "44", name = "双缝实验", category = "经典实验", imageUrl = R.drawable.double_slit, description = "展示了光的波动性和粒子性，揭示了量子力学的基本性质。", price = "¥6"),
    Product(id = "45", name = "迈克尔逊-莫雷实验", category = "经典实验", imageUrl = R.drawable.michelson_morley, description = "旨在检测以太存在的实验，结果支持了相对论。", price = "¥6"),
    Product(id = "46", name = "伽利略的自由落体实验", category = "经典实验", imageUrl = R.drawable.free_fall, description = "证明了物体下落速度与其重量无关。", price = "¥6"),
    Product(id = "47", name = "卡文迪许扭秤实验", category = "经典实验", imageUrl = R.drawable.cavendish_experiment, description = "用于测量地球的质量和万有引力常数。", price = "¥6"),

    Product(id = "48", name = "光的折射", category = "物理现象", imageUrl = R.drawable.refraction, description = "描述光在不同介质中传播时方向发生改变的现象。", price = "¥6"),
    Product(id = "49", name = "布朗运动", category = "物理现象", imageUrl = R.drawable.brownian_motion, description = "描述微小粒子在流体中无规则运动的现象。", price = "¥6"),
    Product(id = "50", name = "磁场", category = "物理现象", imageUrl = R.drawable.magnetic_field, description = "由运动电荷产生的现象，表现为磁力的作用。", price = "¥6"),
    Product(id = "51", name = "超导现象", category = "物理现象", imageUrl = R.drawable.superconductivity, description = "在低温下电阻降为零的现象。", price = "¥6"),

    Product(id = "52", name = "粒子加速器", category = "尖端技术", imageUrl = R.drawable.particle_accelerator, description = "用于加速亚原子粒子进行高能物理实验的设备。", price = "¥6"),
    Product(id = "53", name = "量子计算机", category = "尖端技术", imageUrl = R.drawable.quantum_computer, description = "利用量子叠加和纠缠原理进行计算的新型计算机。", price = "¥6"),
    Product(id = "54", name = "核聚变反应堆", category = "尖端技术", imageUrl = R.drawable.fusion_reactor, description = "通过核聚变产生能量的装置，有望成为未来的清洁能源。", price = "¥6"),
    Product(id = "55", name = "纳米技术", category = "尖端技术", imageUrl = R.drawable.nanotechnology, description = "研究和应用纳米尺度物质的技术，广泛应用于材料科学、医学等领域。", price = "¥6")
)


    // ... 省略中间的条目以节省空间 ...

@Composable
fun StoreScreen(navController: NavController) {

    val searchTextState = remember { mutableStateOf(TextFieldValue("")) }

    Column {
        StoreTopAppBar(
            text = "商城",
            navController = navController
        )
        ScrollableTapRow(physicistsProducts, navController)
    }
}


