package com.skyappz.namma.model;

public class User implements Cloneable {

    public static String LOGIN_MODE_NORMAL = "1";

    private String country;

    private String cover_image_approve;

    private String education;

    private String occupation;

    private String paadham;

    private String login_type;

    private String food_habits;

    private String disability;

    private String mother_name;

    private String password;

    private String profile_image;

    private String working_sector;

    private String no_of_siblings;

    private String body_type;

    private String profile_created_for;

    private String state;

    private String dist;

    private String cover_image;

    private String horoscope_approve;

    private String details_added;

    private String raasi;

    private String profile_created_by;

    private String height;

    private String profile_image_approve;

    private String family_status;

    private String device_id;

    private String caste;

    private String smoking_habits;

    private String weight;

    private String active;

    private String no_of_children;

    private String horoscope;

    private String nationality;

    private String user_id;

    private String father_name;

    private String dob;

    private String name;

    private String about_myself;

    private String about_partner;

    private String drinking_habits;

    private String mother_tongue;

    private String sub_caste;

    private String horoscope_id;

    private String gender;

    private String min_income;

    private String dosham_details;

    private String mother_occupation;

    private String family_type;

    private String delete_status;

    private String having_dosham;

    private String about_family;

    private String email;

    private String father_occupation;

    private String paid_status;

    private String star;

    private String id_proof;

    private String residency_address;

    private String complexion;

    private String physical_status;

    private String religion;

    private String home_city;

    private String marital_status;

    private String feet_inch;

    private String id_proof_approve;

    private String eating_habits;

    private String mobile_number;

    private String office_details;

    private String max_income;

    private String age;



    int motherTonguePosition, profileForPosition, religionPosition, castePosition, subCastePosition, smokingHabitsPosition, drinkingHabitsPosition, foodHabitsPosition, familyTypePosition, familyStatusPosition, motherOccupationPosition, fatherOccupationPosition, complexionsPositions, disabilityPosition, bodyTypesPosition, educationPosition, occupationPosition, workingSectorPosition;

    public User() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCover_image_approve() {
        return cover_image_approve;
    }

    public void setCover_image_approve(String cover_image_approve) {
        this.cover_image_approve = cover_image_approve;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPaadham() {
        return paadham;
    }

    public void setPaadham(String paadham) {
        this.paadham = paadham;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getFood_habits() {
        return food_habits;
    }

    public void setFood_habits(String food_habits) {
        this.food_habits = food_habits;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getWorking_sector() {
        return working_sector;
    }

    public void setWorking_sector(String working_sector) {
        this.working_sector = working_sector;
    }

    public String getNo_of_siblings() {
        return no_of_siblings;
    }

    public void setNo_of_siblings(String no_of_siblings) {
        this.no_of_siblings = no_of_siblings;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getProfile_created_for() {
        return profile_created_for;
    }

    public void setProfile_created_for(String profile_created_for) {
        this.profile_created_for = profile_created_for;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getHoroscope_approve() {
        return horoscope_approve;
    }

    public void setHoroscope_approve(String horoscope_approve) {
        this.horoscope_approve = horoscope_approve;
    }

    public String getDetails_added() {
        return details_added;
    }

    public void setDetails_added(String details_added) {
        this.details_added = details_added;
    }

    public String getRaasi() {
        return raasi;
    }

    public void setRaasi(String raasi) {
        this.raasi = raasi;
    }

    public String getProfile_created_by() {
        return profile_created_by;
    }

    public void setProfile_created_by(String profile_created_by) {
        this.profile_created_by = profile_created_by;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getProfile_image_approve() {
        return profile_image_approve;
    }

    public void setProfile_image_approve(String profile_image_approve) {
        this.profile_image_approve = profile_image_approve;
    }

    public String getFamily_status() {
        return family_status;
    }

    public void setFamily_status(String family_status) {
        this.family_status = family_status;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getSmoking_habits() {
        return smoking_habits;
    }

    public void setSmoking_habits(String smoking_habits) {
        this.smoking_habits = smoking_habits;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getNo_of_children() {
        return no_of_children;
    }

    public void setNo_of_children(String no_of_children) {
        this.no_of_children = no_of_children;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout_myself() {
        return about_myself;
    }

    public void setAbout_myself(String about_myself) {
        this.about_myself = about_myself;
    }

    public String getAbout_partner() {
        return about_partner;
    }

    public void setAbout_partner(String about_partner) {
        this.about_partner = about_partner;
    }

    public String getDrinking_habits() {
        return drinking_habits;
    }

    public void setDrinking_habits(String drinking_habits) {
        this.drinking_habits = drinking_habits;
    }

    public String getMother_tongue() {
        return mother_tongue;
    }

    public void setMother_tongue(String mother_tongue) {
        this.mother_tongue = mother_tongue;
    }

    public String getSub_caste() {
        return sub_caste;
    }

    public void setSub_caste(String sub_caste) {
        this.sub_caste = sub_caste;
    }

    public String getHoroscope_id() {
        return horoscope_id;
    }

    public void setHoroscope_id(String horoscope_id) {
        this.horoscope_id = horoscope_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMin_income() {
        return min_income;
    }

    public void setMin_income(String min_income) {
        this.min_income = min_income;
    }

    public String getDosham_details() {
        return dosham_details;
    }

    public void setDosham_details(String dosham_details) {
        this.dosham_details = dosham_details;
    }

    public String getMother_occupation() {
        return mother_occupation;
    }

    public void setMother_occupation(String mother_occupation) {
        this.mother_occupation = mother_occupation;
    }

    public String getFamily_type() {
        return family_type;
    }

    public void setFamily_type(String family_type) {
        this.family_type = family_type;
    }

    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }

    public String getHaving_dosham() {
        return having_dosham;
    }

    public void setHaving_dosham(String having_dosham) {
        this.having_dosham = having_dosham;
    }

    public String getAbout_family() {
        return about_family;
    }

    public void setAbout_family(String about_family) {
        this.about_family = about_family;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFather_occupation() {
        return father_occupation;
    }

    public void setFather_occupation(String father_occupation) {
        this.father_occupation = father_occupation;
    }

    public String getPaid_status() {
        return paid_status;
    }

    public void setPaid_status(String paid_status) {
        this.paid_status = paid_status;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getId_proof() {
        return id_proof;
    }

    public void setId_proof(String id_proof) {
        this.id_proof = id_proof;
    }

    public String getResidency_address() {
        return residency_address;
    }

    public void setResidency_address(String residency_address) {
        this.residency_address = residency_address;
    }

    public String getComplexion() {
        return complexion;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public String getPhysical_status() {
        return physical_status;
    }

    public void setPhysical_status(String physical_status) {
        this.physical_status = physical_status;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getHome_city() {
        return home_city;
    }

    public void setHome_city(String home_city) {
        this.home_city = home_city;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getFeet_inch() {
        return feet_inch;
    }

    public void setFeet_inch(String feet_inch) {
        this.feet_inch = feet_inch;
    }

    public String getId_proof_approve() {
        return id_proof_approve;
    }

    public void setId_proof_approve(String id_proof_approve) {
        this.id_proof_approve = id_proof_approve;
    }

    public String getEating_habits() {
        return eating_habits;
    }

    public void setEating_habits(String eating_habits) {
        this.eating_habits = eating_habits;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getOffice_details() {
        return office_details;
    }

    public void setOffice_details(String office_details) {
        this.office_details = office_details;
    }

    public String getMax_income() {
        return max_income;
    }

    public void setMax_income(String max_income) {
        this.max_income = max_income;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getMotherTonguePosition() {
        return motherTonguePosition;
    }

    public void setMotherTonguePosition(int motherTonguePosition) {
        this.motherTonguePosition = motherTonguePosition;
    }

    public int getProfileForPosition() {
        return profileForPosition;
    }

    public void setProfileForPosition(int profileForPosition) {
        this.profileForPosition = profileForPosition;
    }

    public int getReligionPosition() {
        return religionPosition;
    }

    public void setReligionPosition(int religionPosition) {
        this.religionPosition = religionPosition;
    }

    public int getCastePosition() {
        return castePosition;
    }

    public void setCastePosition(int castePosition) {
        this.castePosition = castePosition;
    }

    public int getSubCastePosition() {
        return subCastePosition;
    }

    public void setSubCastePosition(int subCastePosition) {
        this.subCastePosition = subCastePosition;
    }

    public int getSmokingHabitsPosition() {
        return smokingHabitsPosition;
    }

    public void setSmokingHabitsPosition(int smokingHabitsPosition) {
        this.smokingHabitsPosition = smokingHabitsPosition;
    }

    public int getDrinkingHabitsPosition() {
        return drinkingHabitsPosition;
    }

    public void setDrinkingHabitsPosition(int drinkingHabitsPosition) {
        this.drinkingHabitsPosition = drinkingHabitsPosition;
    }

    public int getFoodHabitsPosition() {
        return foodHabitsPosition;
    }

    public void setFoodHabitsPosition(int foodHabitsPosition) {
        this.foodHabitsPosition = foodHabitsPosition;
    }

    public int getFamilyTypePosition() {
        return familyTypePosition;
    }

    public void setFamilyTypePosition(int familyTypePosition) {
        this.familyTypePosition = familyTypePosition;
    }

    public int getFamilyStatusPosition() {
        return familyStatusPosition;
    }

    public void setFamilyStatusPosition(int familyStatusPosition) {
        this.familyStatusPosition = familyStatusPosition;
    }

    public int getMotherOccupationPosition() {
        return motherOccupationPosition;
    }

    public void setMotherOccupationPosition(int motherOccupationPosition) {
        this.motherOccupationPosition = motherOccupationPosition;
    }

    public int getFatherOccupationPosition() {
        return fatherOccupationPosition;
    }

    public void setFatherOccupationPosition(int fatherOccupationPosition) {
        this.fatherOccupationPosition = fatherOccupationPosition;
    }

    public int getComplexionsPositions() {
        return complexionsPositions;
    }

    public void setComplexionsPositions(int complexionsPositions) {
        this.complexionsPositions = complexionsPositions;
    }

    public int getDisabilityPosition() {
        return disabilityPosition;
    }

    public void setDisabilityPosition(int disabilityPosition) {
        this.disabilityPosition = disabilityPosition;
    }

    public int getBodyTypesPosition() {
        return bodyTypesPosition;
    }

    public void setBodyTypesPosition(int bodyTypesPosition) {
        this.bodyTypesPosition = bodyTypesPosition;
    }

    public int getEducationPosition() {
        return educationPosition;
    }

    public void setEducationPosition(int educationPosition) {
        this.educationPosition = educationPosition;
    }

    public int getOccupationPosition() {
        return occupationPosition;
    }

    public void setOccupationPosition(int occupationPosition) {
        this.occupationPosition = occupationPosition;
    }

    public int getWorkingSectorPosition() {
        return workingSectorPosition;
    }

    public void setWorkingSectorPosition(int workingSectorPosition) {
        this.workingSectorPosition = workingSectorPosition;
    }

    public User duplicate(User user) {
        User duplicate = new User();

        duplicate.country = this.country;

        duplicate.cover_image_approve = this.cover_image_approve;

        duplicate.education = this.education;

        duplicate.occupation = this.occupation;

        duplicate.paadham = this.paadham;

        duplicate.login_type = this.login_type;

        duplicate.food_habits = this.food_habits;

        duplicate.disability = this.disability;

        duplicate.mother_name = this.mother_name;

        duplicate.password = this.password;

        duplicate.profile_image = this.profile_image;

        duplicate.working_sector = this.working_sector;

        duplicate.no_of_siblings = this.no_of_siblings;

        duplicate.body_type = this.body_type;

        duplicate.profile_created_for = this.profile_created_for;

        duplicate.state = this.state;

        duplicate.dist = this.dist;

        duplicate.cover_image = this.cover_image;

        duplicate.horoscope_approve = this.horoscope_approve;

        duplicate.details_added = this.details_added;

        duplicate.raasi = this.raasi;

        duplicate.profile_created_by = this.profile_created_by;

        duplicate.height = this.height;

        duplicate.profile_image_approve = this.profile_image_approve;

        duplicate.family_status = this.family_status;

        duplicate.device_id = this.device_id;

        duplicate.caste = this.caste;

        duplicate.smoking_habits = this.smoking_habits;

        duplicate.weight = this.weight;

        duplicate.active = this.active;

        duplicate.no_of_children = this.no_of_children;

        duplicate.horoscope = this.horoscope;

        duplicate.nationality = this.nationality;

        duplicate.user_id = this.user_id;

        duplicate.father_name = this.father_name;

        duplicate.dob = this.dob;

        duplicate.name = this.name;

        duplicate.about_myself = this.about_myself;

        duplicate.about_partner = this.about_partner;

        duplicate.drinking_habits = this.drinking_habits;

        duplicate.mother_tongue = this.mother_tongue;

        duplicate.sub_caste = this.sub_caste;

        duplicate.horoscope_id = this.horoscope_id;

        duplicate.gender = this.gender;

        duplicate.min_income = this.min_income;

        duplicate.dosham_details = this.dosham_details;

        duplicate.mother_occupation = this.mother_occupation;

        duplicate.family_type = this.family_type;

        duplicate.delete_status = this.delete_status;

        duplicate.having_dosham = this.having_dosham;

        duplicate.about_family = this.about_family;

        duplicate.email = this.email;

        duplicate.father_occupation = this.father_occupation;

        duplicate.paid_status = this.paid_status;

        duplicate.star = this.star;

        duplicate.id_proof = this.id_proof;

        duplicate.residency_address = this.residency_address;

        duplicate.complexion = this.complexion;

        duplicate.physical_status = this.physical_status;

        duplicate.religion = this.religion;

        duplicate.home_city = this.home_city;

        duplicate.marital_status = this.marital_status;

        duplicate.feet_inch = this.feet_inch;

        duplicate.id_proof_approve = this.id_proof_approve;

        duplicate.eating_habits = this.eating_habits;

        duplicate.mobile_number = this.mobile_number;

        duplicate.office_details = this.office_details;

        duplicate.max_income = this.max_income;

        duplicate.age = this.age;

        return duplicate;
    }
}
