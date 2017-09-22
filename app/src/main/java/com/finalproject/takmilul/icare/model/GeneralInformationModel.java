package com.finalproject.takmilul.icare.model;

import java.util.ArrayList;

public class GeneralInformationModel {
    private String name;
    private String description;
    private static ArrayList<GeneralInformationModel> al;
    public GeneralInformationModel(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static ArrayList<GeneralInformationModel> getAllData(){
        al = new ArrayList<GeneralInformationModel>();
        al.add(new GeneralInformationModel("1.Drinking Water:","Maximum activities of human body are dependent on water for their proper functions. We should drink a gallon of water every day."));
        al.add(new GeneralInformationModel("2.Proper Exercises:","For a hygienic body daily or interval base exercises are necessary which keep body fit."));
        al.add(new GeneralInformationModel("3.Use of Balanced Diet:","It is recommended to use such a diet which contains all proper ingredients like protein, carbohydrates, vitamins and iron"));
        al.add(new GeneralInformationModel("4.Proper Sleeping:","A suitable sleeping is the most important thing for a fit body and its absence may cause a lot of disorder and even severe disease."));
        al.add(new GeneralInformationModel("5.Taking Rest:","All human body organs needed rest for their normal functions human body is like a machine and if this machine is over worked is may lose its balance."));
        al.add(new GeneralInformationModel("6.Entertaining Activities:","For proper work human brain and body need aesthetic activities which make your mind work properly that is control center of whole human body."));
        al.add(new GeneralInformationModel("7.Participating in Sports:","In-door and outdoor games are key for a successful life. Games which demand mental exertion and physical energy are important for good health."));
        al.add(new GeneralInformationModel("8.Cleanliness:","Cleanliness is the part of most civilization’s moral values as well as part of world religions."));
        al.add(new GeneralInformationModel("9.Following a schedule:","Keeping to a regular schedule has both physical and mental benefits. So it is suggested to follow a proper schedule."));
        al.add(new GeneralInformationModel("10.Quit Smoking:","Smoking can cause lung disease by damaging your airways and the small air sacs (alveoli) found in your lungs. "));
        al.add(new GeneralInformationModel("11.Take care of your skin:","Always wear sun-screen lotion during summers. It is advisable to use winter care creams to overcome the harsh and cold winds."));
        al.add(new GeneralInformationModel("12.Slow down on the junk:","Research shows that eating too many high-fat-food contributes to high blood-cholesterol levels, which can cause hardening of the arteries, coronary heart disease and stroke."));
        al.add(new GeneralInformationModel("13.Coffee is good:","Researchers have found that two to four cups of coffee daily can lower the risk of colon cancer by 25 per cent."));
        al.add(new GeneralInformationModel("14.Lower your cholesterol:","Work on reducing your cholesterol. This can reduce the risk of heart attack and stroke even when your level is not high. Exercise to reduce weight."));
        al.add(new GeneralInformationModel("15.Socialising is good:"," Meeting friends and relatives is recommended. Weekly socialising improves thememory, concentration and problem solving skills."));
        al.add(new GeneralInformationModel("16.Fruits and vegetables help:","Have at least five portions of vegetables and fruit a day, especially tomatoes, red grapes."));
        al.add(new GeneralInformationModel("17.Vitamins are vital:","A multivit a day keeps the tablet away, but be sure it contains at least 200 meg of folic acid."));
        al.add(new GeneralInformationModel("18.Being overweight is dangerous:","Loose the extra kilos. Over weight people cut 20 weeks of their life for every excess kilogram, according to new research.Keeping a personal weight machine at home really helps. Buy one now!"));
        al.add(new GeneralInformationModel("19.Supplement with selenium:"," Research has shown that people who took a daily supplement of selenium had a 37 per cent reduction in cancers."));
        al.add(new GeneralInformationModel("20.Change your job:","If the workplace is what bothers you. Simply quit! Consider becoming a salesperson. Salespeople are least likely to have a work-related illness."));
        al.add(new GeneralInformationModel("21.List of Foods High in Minerals & vitamins :","Like vitamins, minerals help your body grow and stay healthy. Your body needs minerals to perform different  functions, from building healthy bones, some minerals help maintain a normal heart beat and make hormones.\n" +
                "Magnesium, Calcium and Potassium (Foods): Whole grains, wheat bran, soybean flour, whole-wheat flour, oat bran, spinach, potatoes, tomatoes, avocados, orange juice, bananas,  cheeses, kale, cabbage, turnip greens,  broccoli,   canned salmon, oysters, hazelnuts, yogurt, milk .\n" +
                "Sodium, Phosphorus and Chloride (Foods): Table salt, milk, beets and celery, eggs, dairy products, fish, meat, poultry, legumes , Tomatoes, lettuce, seaweed, rye, olives.\n" +
                "Iron, Manganese and Zinc (Foods) :  Dried peas and beans, nuts and seeds,  whole grains, pumpkin, mushrooms. red meat, Pineapples, wheat germ.\n" +
                "Copper, Selenium and Molybdenum (Foods): Enriched cereals, navy beans, lentils, soybeans, mushrooms, potatoes, tomatoes, sweet potatoes and organ meats, grain products and nuts.\n" +
                "Iodine and Chromium Molybdenum (Foods): Lima beans, soybeans, garlic, sesame seeds, Swiss chard, seafood, spinach, turnip greens, oatmeal, mushrooms, asparagus, whole grains, organ meats, nuts and prunes \n"));
        al.add(new GeneralInformationModel("22.What Foods Should you Eat When you are on a Diet?","A healthy diet provides your body with the vitamins, minerals and other nutrients your cells need to function normally. When you are on a diet, the majority of your diet should consist of fruits and vegetables. You should also consume lean proteins. Foods with lean protein may come from plant sources. Nuts and seeds should also make up a portion of your diet. These foods provide a rich source of dietary fiber to help you feel full and protein to help maintain and repair your tissues."));
        al.add(new GeneralInformationModel("23.Water & 6 Other Weight Loss Drinks :","1. Ice cold water :You can burn up to an extra 100 calories per day drinking icecold \n" +
                "water \n" +
                "2. Fat Free Milk : Milk is high in calcium . your diet can help you lose weight faster .\n" +
                "3. Green Tea : You'll burn 35-to-43% more fat daily drinking 3-to-5 cups of green tea.\n" +
                "4. Vegetable Juice or V8: Drinking a glass of vegetable juice before your meals.\n" +
                "5. Coffee:\n" +
                "You shouldn't drink more than 1-to-2 cups per day .\n" +
                "6. Yogurt Based Smoothies:\n" +
                "This Study shows you can lose 61% more fat and 81% more belly fat when adding yogurt to your diet.\n" +
                "7. Whey protein :\n" +
                "•\tWhey protein helps you lose more fat and maintain more lean muscle while on a weight loss diet.\n"));
        al.add(new GeneralInformationModel("24.Pregnancy Diet & Nutrition: What to Eat, What Not to Eat:","Key pregnancy nutrition:\n" +
                "Folic acid, Calcium, Iron, Protein.\n" +
                "Foods to eat:\n" +
                "Fruits and vegetables: Pregnant women should focus on fruits and vegetables.\n" +
                "Lean protein: such as meat, poultry, fish, eggs, beans, tofu, cheese, milk and nuts.\n" +
                "Whole grains : Whole grains are an important source of energy in the diet, and they also provide fiber, iron and B-vitamins.\n" +
                "Dairy: Aim for 3 to 4 servings of dairy foods a day, such as milk, yogurt and cheese, \n" +
                "Foods to limit: Caffeine, salmon and sardines fish.\n" +
                "Foods to avoid: Alcohol, Fish with high levels of mercury, Unpasteurized food, Raw meat.\n"));
        al.add(new GeneralInformationModel("25.Power Foods All Kids Need:","Calcium: Best Sources\n" +
                "* cheese\n" +
                "* yogurt\n" +
                "* milk\n" +
                "* fortified foods like cereals\n" +
                "* waffles\n" +
                "* juice\n" +
                "* soy milk\n" +
                "Vitamin E: Best Sources\n" +
                "* avocado\n" +
                "* nuts\n" +
                "* peanut butter\n" +
                "* sunflower seeds\n" +
                "* plant oils\n" +
                "* tomato sauce\n" +
                "* wheat germ\n" +
                "* spinach\n" +
                "Fiber: Best Sources\n" +
                "* fruits (raspberries, blackberries, pears, oranges, and apples are some of the best)\n" +
                "* high-fiber cereal\n" +
                "* beans\n" +
                "* lentils\n" +
                "* chickpeas\n" +
                "* whole-grain bread and pasta\n" +
                "* oatmeal\n" +
                "* popcorn\n" +
                "* nuts\n" +
                "* ground flaxseed\n" +
                "* sweet potatoes\n" +
                "* green peas\n" +
                "Potassium: Best Sources\n" +
                "* bananas\n" +
                "* oranges and orange juice\n" +
                "* white and sweet potatoes\n" +
                "* yogurt\n" +
                "* milk\n" +
                "* cantaloupe\n" +
                "* honeydew\n" +
                "* dried apricots\n" +
                "* tomatoes, tomato sauce\n" +
                "* fish such as halibut and cod\n" +
                "Iron: Best Sources\n" +
                "* shrimp\n" +
                "* beef\n" +
                "* chicken\n" +
                "* beans\n" +
                "* lentils\n" +
                "* chickpeas\n" +
                "* tomato paste\n" +
                "* soy nuts\n" +
                "* raisins\n" +
                "* whole wheat bread\n" +
                "* fortified cold and hot cereals (check labels)\n"));
        al.add(new GeneralInformationModel("26.Which Adult Immunizations Do You Need?","Influenza *, 2\n" +
                "Tetanus, diphtheria, pertussis (Td/Tdap) *, 3\n" +
                "Varicella *, 4\n" +
                "Human papillomavirus (HPV) Female *, 5\n" +
                "Human papillomavirus (HPV) Male *, 5\n" +
                "Zoster 6\n" +
                "Measles, mumps, rubella (MMR) *, 7\n" +
                "Pneumococcal 13-valent conjugate (PCV13) *,8\n" +
                "Pneumococcal polysaccharide (PPSV23) 8\n" +
                "Hepatitis A *,9\n" +
                "Hepatitis B *, 10\n" +
                "Meningococcal 4-valent conjugate (MenACWY) or polysaccharide (MPSV4) *, 11\n" +
                "Meningococcal B (MenB) *, 11\n" +
                "Haemophilus influenzae type b (Hib) *, 12\n"));
        al.add(new GeneralInformationModel("27.Child development and trauma guide:","During adolescence, teens often struggle with their body changes, mood swings and social issues. The following information needs to be understood on child development:\n" +
                "• changing health needs for diet, rest, exercise, hygiene and dental care • puberty, menstruation, sexuality and contraception• developing identity based on gender and culture• girls have ‘best friends’, boys have ‘mates’• more responsible in tasks at home, school and work • experiences emotional turmoil, strong feelings and unpredictable mood swings• thinks logically, abstractly and solves problems thinking like an adult • may take an interest in/develop opinions about community or world events• career choice may be realistic, or at odds with school performance and talents.\n"));
        al.add(new GeneralInformationModel("28.Calculate Your Body Mass Index:","BMI is a measurement of body fat based on height and weight that applies to both men and women between the ages of 18 and 65 years. A healthy BMI score is between 20 and 25. \n" +
                "Calculate your Body Mass Index (BMI) :\n" +
                "Total weight (pounds) / Total height (feet)    = BMI Index.\n" +
                "** 1pounds = 1 kg*2.2046 **\n" +
                "BMI Categories: \n" +
                "Underweight = <18.5\n" +
                "Normal weight = 18.5–24.9 \n" +
                "Overweight = 25–29.9 \n" +
                "Obesity = BMI of 30 or greater\n"));
        return al;
    }
}
