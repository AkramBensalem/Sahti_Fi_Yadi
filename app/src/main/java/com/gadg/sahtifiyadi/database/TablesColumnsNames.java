package com.gadg.sahtifiyadi.database;

import android.provider.BaseColumns;

public class TablesColumnsNames {
    public interface CommuneNames {
        public static final String _NAME= "name";
    }
   public interface OtherCommuneColons{
       public static final String _ID_FIREBASE= "id_firebase";
       public static final String _IMAGE= "image";
    }
    public interface CommuneColonsActors{
        public static final String _PLACE = "place";
        public static final String _WILAYA= "wilaya";
        public static final String _COMMUNE= "commune";
        public static final String _PHONE= "phone";
    }

    // Table Name
    public static final String TABLE_NAME_PHARMACIE = "Pharmacies";
    public static final String TABLE_NAME_HOSPITAL = "hospital";
    public static final String TABLE_NAME_DOCTORS = "doctors";
    public static final String TABLE_NAME_SPECIALITY = "speciality";
    public static final String TABLE_NAME_LABORATOIR = "laboratoir";
    public static final String TABLE_NAME_MESSAGES = "messages";
    public static final String TABLE_NAME_DONATEUR = "donateurs";


   public class Pharmacy_columns implements BaseColumns,CommuneNames,OtherCommuneColons,CommuneColonsActors{
       // Table pharmacies columns
       public static final String _TIME = "time";
       public static final String _DESCRIPTION="description";
   }

    public class Etablissement_columns implements BaseColumns,CommuneNames,OtherCommuneColons,CommuneColonsActors {
        // Table Hopital columns
        public static final String _TYPE = "type";
        public static final String _SERVICE = "service";
        public static final String _DESCRIPTION = "description";
    }
    public class Doctor_columns implements BaseColumns,CommuneNames,OtherCommuneColons,CommuneColonsActors {
        // Table doctor columns
        public static final String _SPECIALITY = "specaility";
        public static final String _SERVICE = "service";
        public static final String _TYPE = "type";
        public static final String _TIME = "time";
    }
    public class Speciality_columns implements BaseColumns,CommuneNames {
        // Table doctor specialité columns
        public static final String SPECIALITY_NUMBER = "number";
    }

    public class Message_columns implements BaseColumns,CommuneNames,OtherCommuneColons {
        //Table message columns
        public static final String RECENT_MESSAGE = "recent_message";
        public static final String MESSAGE_RECENT_DATE = "message_recent_date";
        public static final String FULL_MESSAGE = "full_message";
        public static final String IS_READ = "is_read";
    }
    public class Donor_columns implements BaseColumns,CommuneNames,OtherCommuneColons,CommuneColonsActors {
        // Table Donateur columns
        public static final String AGE = "age";
        public static final String GRSANGUIN_DONATEUR = "groupsanguin";
    }

}
