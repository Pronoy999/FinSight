alter database fin_sight set search_path to fin_sight;
drop table if exists txn_sub_category cascade;
CREATE TABLE txn_sub_category
(
    id          SERIAL PRIMARY KEY,
    category_id INT          NOT NULL REFERENCES txn_category (id) ON DELETE CASCADE,
    name        VARCHAR(100) NOT NULL
);

-- Income
INSERT INTO txn_sub_category (category_id, name)
VALUES (1, 'Salary'),
       (1, 'Bonus'),
       (1, 'Freelance'),
       (1, 'Interest Income'),
       (1, 'Dividends'),
       (1, 'Rental Income'),
       (1, 'Capital Gains');

-- Expenses
INSERT INTO txn_sub_category (category_id, name)
VALUES (2, 'Rent'),
       (2, 'Mortgage'),
       (2, 'Property Tax'),
       (2, 'Repairs & Maintenance'),
       (2, 'Electricity'),
       (2, 'Water'),
       (2, 'Gas'),
       (2, 'Internet'),
       (2, 'Phone'),
       (2, 'Groceries'),
       (2, 'Restaurants'),
       (2, 'Coffee Shops'),
       (2, 'Food Delivery'),
       (2, 'Fuel'),
       (2, 'Public Transport'),
       (2, 'Ride Sharing'),
       (2, 'Vehicle Maintenance'),
       (2, 'Insurance'),
       (2, 'Doctor Visits'),
       (2, 'Medicine'),
       (2, 'Health Insurance'),
       (2, 'Gym Membership'),
       (2, 'Tuition'),
       (2, 'Books & Supplies'),
       (2, 'Online Courses'),
       (2, 'School Fees'),
       (2, 'Movies'),
       (2, 'Subscriptions'),
       (2, 'Events & Concerts'),
       (2, 'Games'),
       (2, 'Clothing'),
       (2, 'Electronics'),
       (2, 'Home Appliances'),
       (2, 'Online Shopping'),
       (2, 'Flights'),
       (2, 'Hotels'),
       (2, 'Travel Insurance'),
       (2, 'Sightseeing');

-- Investments
INSERT INTO txn_sub_category (category_id, name)
VALUES (3, 'Mutual Funds'),
       (3, 'Stocks'),
       (3, 'Real Estate'),
       (3, 'Gold'),
       (3, 'Cryptocurrency');

-- Transfers
INSERT INTO txn_sub_category (category_id, name)
VALUES (4, 'Bank Transfer'),
       (4, 'Wallet Top-up'),
       (4, 'Loan to Friend'),
       (4, 'Transfer to Savings');

-- Loans
INSERT INTO txn_sub_category (category_id, name)
VALUES (5, 'Loan EMI Payment'),
       (5, 'Loan Received'),
       (5, 'Interest Paid'),
       (5, 'Interest Received');

-- Taxes & Fees
INSERT INTO txn_sub_category (category_id, name)
VALUES (6, 'Income Tax'),
       (6, 'GST'),
       (6, 'Late Fees'),
       (6, 'Bank Charges');

-- Charity & Donations
INSERT INTO txn_sub_category (category_id, name)
VALUES (7, 'Religious Donations'),
       (7, 'NGO Contributions'),
       (7, 'Crowdfunding Support');

-- Insurance
INSERT INTO txn_sub_category (category_id, name)
VALUES (8, 'Health Insurance'),
       (8, 'Life Insurance'),
       (8, 'Vehicle Insurance'),
       (8, 'Home Insurance');
