package com.skyappz.namma.utils;

import java.util.ArrayList;

public class UserDetailList {

    ArrayList<String> raasi, countries, couples, profilesFor, religions, cities, states, degrees, jobs, companies, languages, maritalStatuses, physicalStatuses, doshams, bodyTypes, complexions, eatingHabits, drinkingHabits, smokingHabits, caste, jobTypes;

    public UserDetailList() {
        profilesFor = new ArrayList<>();
        religions = new ArrayList<>();
        cities = new ArrayList<>();
        states = new ArrayList<>();
        degrees = new ArrayList<>();
        jobs = new ArrayList<>();
        companies = new ArrayList<>();
        languages = new ArrayList<>();
        maritalStatuses = new ArrayList<>();
        physicalStatuses = new ArrayList<>();
        bodyTypes = new ArrayList<>();
        complexions = new ArrayList<>();
        eatingHabits = new ArrayList<>();
        drinkingHabits = new ArrayList<>();
        smokingHabits = new ArrayList<>();
        couples = new ArrayList<>();
        countries = new ArrayList<>();
        raasi = new ArrayList<>();
        caste = new ArrayList<>();
        doshams = new ArrayList<>();
        jobTypes = new ArrayList<>();

        profilesFor.add("Select Matrimony Profile For");
        profilesFor.add("Myself");
        profilesFor.add("Son");
        profilesFor.add("Daughter");
        profilesFor.add("Brother");
        profilesFor.add("Sister");
        profilesFor.add("Relative");
        profilesFor.add("Friend");
        profilesFor.add("Others");

        couples.add("Searching for");
        couples.add("Bride");
        couples.add("Groom");

        religions.add("Select a Religion");
        religions.add("Hindu");
        religions.add("Christian");
        religions.add("Muslim-Shia");
        religions.add("Muslim-Sunni");
        religions.add("Muslim-Others");
        religions.add("Jain-Digambar");
        religions.add("Jain-Shwetambar");
        religions.add("Jain-Others");
        religions.add("Parsi");
        religions.add("Buddhist");
        religions.add("Others");

        countries.add("Select a Country");
        countries.add("Indian");
        countries.add("Sri Lankan");
        countries.add("Russian");
        countries.add("American");
        countries.add("Pakistanian");
        countries.add("British");
        countries.add("Irish");
        countries.add("Brazilian");
        countries.add("Italian");
        countries.add("Chinese");
        countries.add("Polish");
        countries.add("Austrian");
        countries.add("Canadian");
        countries.add("Malaysian");
        countries.add("South Korean");
        countries.add("North Korean");
        countries.add("German");
        countries.add("Swedish");
        countries.add("French");
        countries.add("Swiss");
        countries.add("Others");

        maritalStatuses.add("Select a Marital Status");
        maritalStatuses.add("Unmarried");
        maritalStatuses.add("Married");
        maritalStatuses.add("Divorced");
        maritalStatuses.add("Widowed");
        maritalStatuses.add("Others");

        physicalStatuses.add("Select your Physical status");
        physicalStatuses.add("Normal");
        physicalStatuses.add("Physically Challenged");
        physicalStatuses.add("Others");
        physicalStatuses.add("Don't wish to Specify");

        bodyTypes.add("Select your Body Type");
        bodyTypes.add("Slim");
        bodyTypes.add("Fat");
        bodyTypes.add("Normal");
        bodyTypes.add("Don't wish t specify");

        complexions.add("Select your Complexion Type");
        complexions.add("Fair");
        complexions.add("Very Fair");
        complexions.add("Wheatish");
        complexions.add("Dusky");
        complexions.add("Others");
        complexions.add("Don't wish to specify");

        countries.add("Select your Food Habits");
        eatingHabits.add("Vegetarian");
        eatingHabits.add("Non Vegetarian");
        eatingHabits.add("Eggetarian");

        countries.add("Select a Country");
        drinkingHabits.add("Yes");
        drinkingHabits.add("No");
        drinkingHabits.add("Don't wish to specify");

        smokingHabits.add("Select your Smoking Habits");
        smokingHabits.add("Yes");
        smokingHabits.add("No");
        smokingHabits.add("Don't wish to specify");

        raasi.add("Select your Raasi");
        raasi.add("Aries");
        raasi.add("Taurus");
        raasi.add("Gemini");
        raasi.add("Cancer");
        raasi.add("Leo");
        raasi.add("Virgo");
        raasi.add("Libra");
        raasi.add("Scorpio");
        raasi.add("Sagittarius");
        raasi.add("Capricorn");
        raasi.add("Aquarius");
        raasi.add("Pisces");
        raasi.add("Others");

        caste.add("Choose your Caste");
        caste.add("24 Manai Telugu Chettiar");
        caste.add("Aaru Nattu Vellala");
        caste.add("Achirapakkam Chettiar");
        caste.add("Adi Dravidar");
        caste.add("Agamudayar/Arcot/Thuluva Vellala");
        caste.add("Agaran Vellan Chettiar");
        caste.add("Ahirwar");
        caste.add("Arunthathiyar");
        caste.add("Ayira Vysya");
        caste.add("Badaga");
        caste.add("Bairwa");
        caste.add("Balai");
        caste.add("Beri Chettiar");
        caste.add("Boyar");
        caste.add("Brahmin - Anaviln Desai");
        caste.add("Brahmin - Baidhiki/Vaidhiki");
        caste.add("Brahmin - Bardai");
        caste.add("Brahmin - Bhargav");
        caste.add("Brahmin - Gurukkal");
        caste.add("Brahmin - Iyengar");
        caste.add("Brahmin - Iyer");
        caste.add("Brahmin - Khadayata");
        caste.add("Brahmin - Khedaval");
        caste.add("Brahmin - Mevada");
        caste.add("Brahmin - Rajgor");
        caste.add("Brahmin - Rarhi/Radhi");
        caste.add("Brahmin - Sarua");
        caste.add("Brahmin - Shri Gaud");
        caste.add("Brahmin - Tapodhan");
        caste.add("Brahmin - Valam");
        caste.add("Brahmin - Zalora");
        caste.add("Brahmin - Sri Vaishnava");
        caste.add("Brahmin - Cherakula Vellalar");
        caste.add("Brahmin - Chettiar");
        caste.add("Brahmin - Desikar");
        caste.add("Brahmin - Desikar Tanjavur");
        caste.add("Brahmin - Devandra Kula Vellalar");
        caste.add("Brahmin - Devanga Chettiar");
        caste.add("Brahmin - Devar/Thevar/Mukkulathor");
        caste.add("Brahmin - Dhanak");
        caste.add("Elur Chetty");
        caste.add("Gandla/Ganiga");
        caste.add("Gounder");
        caste.add("Gounder - Kongu - Vellala Gounder");
        caste.add("Gounder - Nattu Gounder");
        caste.add("Gounder - Others");
        caste.add("Gounder - Uralii Gounder");
        caste.add("Gounder - Vanniya Kula Kshatriyar");
        caste.add("Gounder - Vettuva Gounder");
        caste.add("Gramani");
        caste.add("Gurukkal Brahmin");
        caste.add("Illaththu Pillai");
        caste.add("Intercaste");
        caste.add("Isai Vellalar");
        caste.add("Iyenga Brahmin");
        caste.add("Iyer Brahmin");
        caste.add("Julaha");
        caste.add("Kamma Naidu");
        caste.add("Kanaka Padanna");
        caste.add("Kandara");
        caste.add("Karkathar");
        caste.add("Karuneegar");
        caste.add("Kasukara");
        caste.add("Kerala Mudali");
        caste.add("Khatik");
        caste.add("Kodikal Pillai");
        caste.add("Kongu Chettiar");
        caste.add("Kongu Nadar");
        caste.add("Kongu Vellala Gounder");
        caste.add("Kori/Koli");
        caste.add("Krishnavaka");
        caste.add("Kshatriya Raju");
        caste.add("Kulalar");
        caste.add("Kuravan");
        caste.add("Kuruhini Chetty");
        caste.add("Kurumbar");
        caste.add("Kuruva");
        caste.add("Manja Pudhu Chettiar");
        caste.add("Mannan/Velan/Vannan");
        caste.add("Maruthuvar");
        caste.add("Meenavar");
        caste.add("Meghwal");
        caste.add("Mudaliyar");
        caste.add("Mukkulathor");
        caste.add("Muthuraja");
        caste.add("Nadar");
        caste.add("Naicker");
        caste.add("Naicker - Others");
        caste.add("Naicker - Vanniya Kula");
        caste.add("Naicker - Kshatriyar");
        caste.add("Naidu");
        caste.add("Nanjil Mudali");
        caste.add("Nanjil Nattu Vellalar");
        caste.add("Nanjil Vellalar");
        caste.add("Nanjil Pillai");
        caste.add("Nankudi Vellalar");
        caste.add("Nattu Gounder");
        caste.add("Nattukudi Chettiar");
        caste.add("Othuvaar");
        caste.add("Padanashali");
        caste.add("Pallan/Devandrakula Vellalar");
        caste.add("Panan");
        caste.add("Pandaram");
        caste.add("Pandiya Vellalar");
        caste.add("Pannirandam Chettiar");
        caste.add("Paravan/Bharatar");
        caste.add("Parkavakulam/Udayar");
        caste.add("Parkavakulam/Udayar");
        caste.add("Pattinavar");
        caste.add("Pattusali");
        caste.add("Pillai");
        caste.add("Poundra");
        caste.add("Pulaya/Cheruman");
        caste.add("Reddy");
        caste.add("Rohit/Chamar");
        caste.add("SC");
        caste.add("ST");
        caste.add("Sadhu Chetty");
        caste.add("Saiva Pillai Thanjavar");
        caste.add("Saiva Pillai Tirunelveli");
        caste.add("Saiva Velllan Chettiar");
        caste.add("Samagar");
        caste.add("Sambava");
        caste.add("Satnami");
        caste.add("Senai Thalaivar");
        caste.add("Senguntha Mudaliyar");
        caste.add("Sengunthar/Kaikolar");
        caste.add("Shilpkar");
        caste.add("Sonkar");
        caste.add("Sourashtra");
        caste.add("Sozhia Chetty");
        caste.add("Sozhia Vellalar");
        caste.add("Telugupatti");
        caste.add("Thandan");
        caste.add("Thondai Mandala Vellalar");
        caste.add("Urali Gounder");
        caste.add("Vadambar");
        caste.add("Vadugan");
        caste.add("Vaniya Chettiar");
        caste.add("Vannar");
        caste.add("Vannia Kula Kshatriyar");
        caste.add("Veera Saivm");
        caste.add("Veerakodi Vellalar");
        caste.add("Vellalar");
        caste.add("Vellan Chettiars");
        caste.add("Vettuva Gounder");
        caste.add("Vishwakarma");
        caste.add("Vokkaliga");
        caste.add("Yadav");
        caste.add("Yadava Naidu");
        caste.add("Christan -Anglo - Indian");
        caste.add("Christan -Born Again");
        caste.add("Christan -Born Again");
        caste.add("Christan -Church Of South India");
        caste.add("Christan -Evangelist");
        caste.add("Christan -Jacobite");
        caste.add("Christan -Latin Catholic");
        caste.add("Christan -Malankara Catholic");
        caste.add("Christan -Pentecost");
        caste.add("Christan -Roman - Catholic");
        caste.add("Christan -Seventh - day - Adventist");
        caste.add("Christan -Syiran Catholic");
        caste.add("Christan -Syiran Jacobite");
        caste.add("Christan -Syro Malabar");
        caste.add("Christan -Christan - Others");
        caste.add("Muslim - Ansari");
        caste.add("Muslim - Arain");
        caste.add("Muslim - Awan");
        caste.add("Muslim - Bohra");
        caste.add("Muslim - Dekkani");
        caste.add("Muslim - Dudekula");
        caste.add("Muslim - Hanafi");
        caste.add("Muslim - Jat");
        caste.add("Muslim - Khoja");
        caste.add("Muslim - Lebbai");
        caste.add("Muslim - Malik");
        caste.add("Muslim - Mapila");
        caste.add("Muslim - Maraicar");
        caste.add("Muslim - Memon");
        caste.add("Muslim - Mughal");
        caste.add("Muslim - Pathan");
        caste.add("Muslim - Qureshi");
        caste.add("Muslim - Mapila");
        caste.add("Don't Wish to Specify");
        caste.add("Others");

        doshams.add("Do you have a Dosham?");
        doshams.add("Yes");
        doshams.add("No");
        doshams.add("Don't wish to specify");

        states.add("Select your State");
        states.add("Andra pradesh");
        states.add("Arunachal pradesh");
        states.add("Assam");
        states.add("Bihar");
        states.add("Chhattisgarh");
        states.add("Goa");
        states.add("Gujarat");
        states.add("Haryana");
        states.add("Himachal pradesh");
        states.add("Jammu and kashmir");
        states.add("Jharkhand");
        states.add("Karnataka");
        states.add("Kerala");
        states.add("Madya Pradesh");
        states.add("Maharashtra");
        states.add("Manipur");
        states.add("Meghalaya");
        states.add("Mizoram");
        states.add("Nagaland");
        states.add("Orissa");
        states.add("Punjab");
        states.add("Rajasthan");
        states.add("Sikkim");
        states.add("Tamilnadu");
        states.add("Tripura");
        states.add("Uttaranchal");
        states.add("Uttar pradesh");
        states.add("West bengal");
        states.add("Others");

        degrees.add("Select your degree");
        degrees.add("B.ARCH");
        degrees.add("BCA");
        degrees.add("BE");
        degrees.add("B.PLAN");
        degrees.add("B.sc IT/Computer science");
        degrees.add("B.TECH");
        degrees.add("M.ARCH");
        degrees.add("MCA");
        degrees.add("ME");
        degrees.add("M.sc IT/ Computer science");
        degrees.add("M.S (ENGG)");
        degrees.add("M.TECH");
        degrees.add("PGDCA");
        degrees.add("B.A");
        degrees.add("B.Com");
        degrees.add("B.Ed");
        degrees.add("BFA");
        degrees.add("BFT");
        degrees.add("BLIS");
        degrees.add("BMM");
        degrees.add("B.Sc");
        degrees.add("B.S.W");
        degrees.add("BLIS");
        degrees.add("B.M.M");
        degrees.add("B.Sc");
        degrees.add("B.S.W");
        degrees.add("B.PHIL");
        degrees.add("M.A");
        degrees.add("M.Com");
        degrees.add("M.Ed");
        degrees.add("MFA");
        degrees.add("MLIS");
        degrees.add("M.Sc");
        degrees.add("MSW");
        degrees.add("M.Phil");
        degrees.add("BBA");
        degrees.add("BFM");
        degrees.add("BHM");
        degrees.add("MBA");
        degrees.add("MFM");
        degrees.add("MHM");
        degrees.add("MHRM");
        degrees.add("PGDM");
        degrees.add("B.A.M.S");
        degrees.add("BDS");
        degrees.add("BHMS");
        degrees.add("BSMS");
        degrees.add("B.PHARM");
        degrees.add("BPT");
        degrees.add("BUMS");
        degrees.add("BVSC");
        degrees.add("MBBS");
        degrees.add("B.SC (Nursing)");
        degrees.add("MDS");
        degrees.add("MD/MS (MEDICAL)");
        degrees.add("M.Pharm");
        degrees.add("MPT");
        degrees.add("MVSC");
        degrees.add("BGL");
        degrees.add("B.L");
        degrees.add("LLB");
        degrees.add("L.L.M");
        degrees.add("M.L");
        degrees.add("CA");
        degrees.add("CFA");
        degrees.add("CS");
        degrees.add("ICWA");
        degrees.add("IAS");
        degrees.add("IES");
        degrees.add("IFS");
        degrees.add("IRS");
        degrees.add("IPS");
        degrees.add("Ph.d");
        degrees.add("Diploma");
        degrees.add("Polytechnic");
        degrees.add("Trade school");
        degrees.add("Higher secondary school/High school");
        degrees.add("Others");

        jobs.add("Choose your Job");
        jobs.add("Admin");
        jobs.add("Supervisor");
        jobs.add("Manager");
        jobs.add("Officer");
        jobs.add("Administrative Professional");
        jobs.add("Executive");
        jobs.add("Clerk");
        jobs.add("Human Resources pProfessional");
        jobs.add("Agriculture");
        jobs.add("Agriculture and farming professional");
        jobs.add("Airline");
        jobs.add("Pilot");
        jobs.add("Air Hostess");
        jobs.add("Airline Professionals");
        jobs.add("Architech and Design");
        jobs.add("Architect");
        jobs.add("Interior Designer");
        jobs.add("Banking and finance");
        jobs.add("Chartered accountant");
        jobs.add("Company Secretary");
        jobs.add("Accounts/financial professional");
        jobs.add("Banking service professional");
        jobs.add("Auditor");
        jobs.add("Financial analyst /planning");
        jobs.add("Beauty and Fashion");
        jobs.add("Fashion Designer");
        jobs.add("Beautician");
        jobs.add("Civil service(IAS/IPS/IRS/IES/IFS)");
        jobs.add("Defense");
        jobs.add("Army");
        jobs.add("Navy");
        jobs.add("Air Force");
        jobs.add("Education");
        jobs.add("Professor/Lecturer");
        jobs.add("Education professional");
        jobs.add("Hospitality");
        jobs.add("Hotel/Hospitality Professional");
        jobs.add("IT and Engineering");
        jobs.add("Software professional");
        jobs.add("Hardware professional");
        jobs.add("Engineer Non IT");
        jobs.add("Designer");
        jobs.add("Legal");
        jobs.add("Lawyer and Legal Professional");
        jobs.add("Medical");
        jobs.add("Doctor");
        jobs.add("Health care professional");
        jobs.add("Paramedical professional");
        jobs.add("Nurse");
        jobs.add("Marketing professional");
        jobs.add("Sales professional");
        jobs.add("Journalist");
        jobs.add("Media professional");
        jobs.add("Entertainment professional");
        jobs.add("Event management professional");
        jobs.add("Advertising/PR professional");
        jobs.add("Mariner/merchant navy");
        jobs.add("Scientist");
        jobs.add("Scientist Research");
        jobs.add("CXO\\President,Director,Chairman");
        jobs.add("Business Analyst");
        jobs.add("Consultant");
        jobs.add("Customer care professional");
        jobs.add("Social worker");
        jobs.add("Sportsman");
        jobs.add("Technician");
        jobs.add("Arts and Craftsman");
        jobs.add("Librarian");
        jobs.add("Business  Owner/Entrepreneur.");
        jobs.add("Others");

        jobTypes.add("Choose your Job Type");
        jobTypes.add("Govt");
        jobTypes.add("Private");
        jobTypes.add("Others");

        languages.add("Assamese");
        languages.add("Bengali");
        languages.add("English");
        languages.add("Gujarat");
        languages.add("Hindi");
        languages.add("Kannada");
        languages.add("Malayalam");
        languages.add("Marathi");
        languages.add("Marwari");
        languages.add("Oriya");
        languages.add("Punjabi");
        languages.add("Sindhi");
        languages.add("Tamil");
        languages.add("Telugu");
        languages.add("Urdu");
        languages.add("Others");


        cities.add("Chennai");
        cities.add("Cuddalore");
        cities.add("Kanchipuram");
        cities.add("Tiruvallur");
        cities.add("Tiruvannamalai");
        cities.add("Vellore");
        cities.add("Viluppuram");
        cities.add("Ariyalur");
        cities.add("Nagapattinam");
        cities.add("Perambalur");
        cities.add("Pudukkottai");
        cities.add("Thanjavur");
        cities.add("Tiruchirappalli");
        cities.add("Karur");
        cities.add("Tvaruriru");
        cities.add("Dharmapuri");
        cities.add("Krishnagiri");
        cities.add("Namakkal");
        cities.add("Salem");
        cities.add("Erode");
        cities.add("Tiruppur");
        cities.add("Coimbatore");
        cities.add("Nilgiris");
        cities.add("South");
        cities.add("Madurai");
        cities.add("Theni");
        cities.add("Dindigul");
        cities.add("Sivagangai");
        cities.add("Virudunagar");
        cities.add("Ramanathapuram");
        cities.add("Tirunelveli");
        cities.add("Thoothukudi");
        cities.add("Kanyakumari");
        cities.add("Others");
    }

    public ArrayList<String> getRaasi() {
        return raasi;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public ArrayList<String> getCouples() {
        return couples;
    }

    public ArrayList<String> getProfilesFor() {
        return profilesFor;
    }

    public ArrayList<String> getReligions() {
        return religions;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public ArrayList<String> getDegrees() {
        return degrees;
    }

    public ArrayList<String> getJobs() {
        return jobs;
    }

    public ArrayList<String> getCompanies() {
        return companies;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public ArrayList<String> getMaritalStatuses() {
        return maritalStatuses;
    }

    public ArrayList<String> getPhysicalStatuses() {
        return physicalStatuses;
    }

    public ArrayList<String> getDoshams() {
        return doshams;
    }

    public ArrayList<String> getBodyTypes() {
        return bodyTypes;
    }

    public ArrayList<String> getComplexions() {
        return complexions;
    }

    public ArrayList<String> getEatingHabits() {
        return eatingHabits;
    }

    public ArrayList<String> getDrinkingHabits() {
        return drinkingHabits;
    }

    public ArrayList<String> getSmokingHabits() {
        return smokingHabits;
    }

    public ArrayList<String> getCaste() {
        return caste;
    }

    public ArrayList<String> getJobTypes() {
        return jobTypes;
    }
}
