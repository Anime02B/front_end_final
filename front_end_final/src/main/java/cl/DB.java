package cl;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    public static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/front_end_final",
                    "postgres", "00000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AddType(TypeMoney typeMoney, String sql) {
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, typeMoney.getName());
            st.setString(2, typeMoney.getImg());

            st.executeUpdate();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AddSource(TypeMoney typeMoney) {
        String sql = " INSERT INTO public.source ( " +
                    " name, amount, img) VALUES ( " +
                    " ?, '0', ?) ";
        AddType(typeMoney, sql);
    }

    public static void AddCash(TypeMoney typeMoney) {
        String sql = " INSERT INTO public.cash ( " +
                " name, amount, img) VALUES ( " +
                " ?, '0', ?) ";
        AddType(typeMoney, sql);
    }

    public static void AddExpenses(TypeMoney typeMoney) {
        String sql = " INSERT INTO public.expenses ( " +
                " name, amount, img) VALUES ( " +
                " ?, '0', ?) ";
        AddType(typeMoney, sql);
    }



    public static boolean AddMoney(Money money, String sql) {
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, money.getAmount());
            st.setDate(2, money.getM_date());
            st.setString(3, money.getName());
            st.setInt(4, money.getFrom_type().getId());
            st.setInt(5, money.getTo_type().getId());

            st.executeUpdate();
            st.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void AddIncome(Money money) {
        String sql = " INSERT INTO public.income ( " +
                " amount, m_date, name, from_id, to_id) VALUES ( " +
                " ?, ?, ?, ?, ?) ";

        if (AddMoney(money, sql)) {

            UpdateSource(money.getAmount(), money.getFrom_type().getId());
            UpdateCash(money.getAmount(), money.getTo_type().getId());

        }
    }

    public static void AddOutcome(Money money) {
        String sql = " INSERT INTO public.outcome ( " +
                " amount, m_date, name, from_id, to_id) VALUES ( " +
                " ?, ?, ?, ?, ?) ";

        if (AddMoney(money, sql)) {
            UpdateCash(-money.getAmount(), money.getFrom_type().getId());
            UpdateExpenses(money.getAmount(), money.getTo_type().getId());
        }
    }

    public static void AddTransfer(Money money) {
        String sql = " INSERT INTO public.transfer ( " +
                " amount, m_date, name, from_id, to_id) VALUES ( " +
                " ?, ?, ?, ?, ?) ";
        if (AddMoney(money, sql)) {
            UpdateCash(-money.getAmount(), money.getFrom_type().getId());
            UpdateCash(money.getAmount(), money.getTo_type().getId());
        }
    }

    public static TypeMoney GetTypeMoney (int id, String sql) {
        TypeMoney typeMoney = new TypeMoney();
        System.out.println("scdcdzczdvz" + id);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet res = st.executeQuery();

            while (res.next()) {
                typeMoney = new TypeMoney(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getInt("amount"),
                        res.getString("img")
                );

            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(typeMoney.getId());
        return typeMoney;
    }

    public static TypeMoney GetSource (int id) {
        String sql = "SELECT * FROM public.source " +
                   " WHERE id = ? LIMIT 1 ";

        return GetTypeMoney(id, sql);
    }

    public static TypeMoney GetCash (int id) {
        String sql = "SELECT * FROM public.cash " +
                " WHERE id = ? LIMIT 1 ";
        return GetTypeMoney(id, sql);
    }

    public static TypeMoney GetExpenses (int id) {
        String sql = "SELECT * FROM public.expenses " +
                " WHERE id = ? LIMIT 1 ";
        return GetTypeMoney(id, sql);
    }

    public static void UpdateType (int amount, int id, String sql) {
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, amount);
            st.setInt(2, id);

            st.executeUpdate();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UpdateSource (int amount, int id) {
        String sql = " UPDATE public.source SET amount = amount + ? WHERE id = ?; ";
        UpdateType(amount, id, sql);
    }

    public static void UpdateCash (int amount, int id) {
        String sql = " UPDATE public.cash SET amount = amount + ? WHERE id = ?; ";
        UpdateType(amount, id, sql);
    }
    public static void UpdateExpenses (int amount, int id) {
        String sql = " UPDATE public.expenses SET amount = amount + ? WHERE id = ?; ";
        UpdateType(amount, id, sql);
    }



    public static ArrayList<TypeMoney> getTypeList(String sql) {
        ArrayList<TypeMoney> typeMonies = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                typeMonies.add(new TypeMoney(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getInt("amount"),
                        res.getString("img")
                ));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeMonies;
    }

    public static ArrayList<TypeMoney> GetCashList () {
        String sql = " SELECT * FROM public.cash ";
        return getTypeList(sql);
    }

    public static ArrayList<TypeMoney> GetSourceList () {
        String sql = " SELECT * FROM public.source ";
        return getTypeList(sql);
    }

    public static ArrayList<TypeMoney> GetExpensesList () {
        String sql = " SELECT * FROM public.expenses ";
        return getTypeList(sql);
    }

    public static ArrayList<Money> getIncomeList() {
        ArrayList<Money> moneyList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(" SELECT * FROM public.income " +
                    " ORDER BY m_date DESC; ");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                moneyList.add(new Money(
                        res.getDate("m_date"),
                        res.getInt("amount"),
                        res.getInt("id"),
                        res.getString("name"),
                        GetCash(res.getInt("from_id")),
                        GetCash(res.getInt("to_id"))
                ));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moneyList;
    }

    public static ArrayList<Money> getOutcomeList() {
        ArrayList<Money> moneyList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.outcome " +
                    " ORDER BY m_date DESC; ");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                moneyList.add(new Money(
                        res.getDate("m_date"),
                        res.getInt("amount"),
                        res.getInt("id"),
                        res.getString("name"),
                        GetCash(res.getInt("from_id")),
                        GetCash(res.getInt("to_id"))
                ));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moneyList;
    }

    public static ArrayList<Money> getTransferList() {
        ArrayList<Money> moneyList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.transfer");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                moneyList.add(new Money(
                        res.getDate("m_date"),
                        res.getInt("amount"),
                        res.getInt("id"),
                        res.getString("name"),
                        GetCash(res.getInt("from_id")),
                        GetCash(res.getInt("to_id"))
                ));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moneyList;
    }

    public static ArrayList<Money_for_front> getHistory() {
        ArrayList<Money_for_front> history = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(
                    " SELECT id, amount, m_date, name, from_id, to_id, " +
                    " true AS is_income " +
                    " FROM public.income " +
                    " UNION " +
                    " SELECT id, amount, m_date, name, from_id, to_id, false AS is_income " +
                    " FROM public.outcome " +
                    " ORDER BY m_date DESC; ");
            ResultSet res = st.executeQuery();

            TypeMoney from_type = new TypeMoney();
            TypeMoney to_type = new TypeMoney();

            while (res.next()) {

                if(res.getBoolean("is_income")) {
                    from_type = GetSource(res.getInt("from_id"));
                    to_type = GetCash(res.getInt("to_id"));
                }
                else {
                    from_type = GetCash(res.getInt("from_id"));
                    to_type = GetExpenses(res.getInt("to_id"));
                }


                history.add(new Money_for_front(
                        res.getDate("m_date"),
                        res.getInt("amount"),
                        res.getInt("id"),
                        res.getString("name"),
                        from_type,
                        to_type,
                        res.getBoolean("is_income")
                ));

            }

            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return history;
    }



    public static Long GetSumTypeMoney (String sql) {
        Long sum = 0L;
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet res = st.executeQuery();


            if (res.next()) {
                        sum = res.getLong("sum");
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    public static Long GetSumSource () {
        String sql = " SELECT SUM(amount) FROM public.source; ";
        return GetSumTypeMoney(sql);
    }

    public static Long GetSumCash () {
        String sql = " SELECT SUM(amount) FROM public.cash; ";
        return GetSumTypeMoney(sql);
    }
    public static Long GetSumExpenses () {
        String sql = " SELECT SUM(amount) FROM public.expenses; ";
        return GetSumTypeMoney(sql);
    }




    public static ArrayList<MoneyZero> getIncomeD() {
        ArrayList<MoneyZero> moneyList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(" SELECT m_date, SUM(amount) as total_amount " +
                    " FROM public.income " +
                    " GROUP BY m_date " +
                    " ORDER BY m_date ASC; ");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                moneyList.add(new MoneyZero(
                        res.getDate("m_date"),
                        (int)res.getLong("total_amount")
                ));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return moneyList;
    }

    public static ArrayList<MoneyZero> getOutcomeD() {
        ArrayList<MoneyZero> moneyList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(" SELECT m_date, SUM(amount) as total_amount " +
                    " FROM public.outcome " +
                    " GROUP BY m_date " +
                    " ORDER BY m_date ASC; ");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                moneyList.add(new MoneyZero(
                        res.getDate("m_date"),
                        (int)res.getLong("total_amount")
                ));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moneyList;
    }



    public static ArrayList<Date> getDateListD() {
        ArrayList<Date> dates = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(
                    " SELECT DISTINCT m_date FROM (" +
                    "  SELECT m_date FROM public.income " +
                    "  UNION\n" +
                    "  SELECT m_date FROM public.outcome " +
                    ") AS all_dates ORDER BY m_date ASC; ");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                dates.add(res.getDate("m_date"));
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    public static boolean DeleteMoney(String sql, int id) {

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void DeleteExpenses (int id) {
        String sql = " DELETE FROM public.expenses WHERE id = ?; ";
        DeleteOutcomeT(id);

        DeleteMoney(sql, id);

    }

    public static void DeleteSource (int id) {
        String sql = " DELETE FROM public.source WHERE id = ?; ";
        System.out.println(id);
        DeleteIncomeF(id);

        DeleteMoney(sql,id);
    }

    public static void DeleteCash (int id) {
        String sql = " DELETE FROM public.cash WHERE id = ?; ";
        DeleteOutcomeF(id);
        DeleteIncomeT(id);
        DeleteMoney(sql,id);
    }

    public static void DeleteIncomeF (int id) {
        String sql = " DELETE FROM public.income WHERE from_id = ?; ";
        System.out.println(id);
        DeleteMoney(sql,id);

    }
    public static void DeleteIncomeT (int id) {
        String sql = " DELETE FROM public.income WHERE to_id = ?; ";
        DeleteMoney(sql,id);
    }


    public static void DeleteOutcomeF (int id) {
        String sql = " DELETE FROM public.outcome WHERE from_id = ?; ";
        DeleteMoney(sql,id);
    }

    public static void DeleteOutcomeT (int id) {
        String sql = " DELETE FROM public.outcome WHERE to_id = ?; ";
        DeleteMoney(sql,id);
    }

    public static int GetLimit () {
        int Amount = 0;
        try {
            PreparedStatement st = connection.prepareStatement(" SELECT * FROM public.pizza WHERE id = 1 LIMIT 1 ");
            ResultSet res = st.executeQuery();

            while (res.next()) {
                Amount = res.getInt("amount");
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Amount;
    }

    public static void UpdateLimit (int amount) {
        try {
            PreparedStatement st = connection.prepareStatement(" UPDATE public.pizza SET amount = ? WHERE id = 1; ");

            st.setInt(1, amount);

            st.executeUpdate();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






















}
