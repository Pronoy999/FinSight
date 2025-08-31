-- Set schema
alter database fin_sight set search_path to fin_sight;

-- Drop and recreate the single category table
drop table if exists txn_category cascade;
CREATE TABLE txn_category
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    nature VARCHAR(10)
);

-- Insert all categories and subcategories as flat rows
INSERT INTO txn_category (name, nature)
VALUES
-- Income
('Income', 'Credit'),
('Salary', 'Credit'),
('Bonus', 'Credit'),
('Freelance', 'Credit'),
('Interest Income', 'Credit'),
('Dividends', 'Credit'),
('Rental Income', 'Credit'),
('Capital Gains', 'Credit'),

-- Expenses
('Expenses', 'Debit'),
('Rent', 'Debit'),
('Mortgage', 'Debit'),
('Property Tax', 'Debit'),
('Repairs & Maintenance', 'Debit'),
('Electricity', 'Debit'),
('Water', 'Debit'),
('Gas', 'Debit'),
('Internet', 'Debit'),
('Phone', 'Debit'),
('Groceries', 'Debit'),
('Restaurants', 'Debit'),
('Coffee Shops', 'Debit'),
('Food Delivery', 'Debit'),
('Fuel', 'Debit'),
('Public Transport', 'Debit'),
('Ride Sharing', 'Debit'),
('Vehicle Maintenance', 'Debit'),
('Insurance', 'Debit'),
('Doctor Visits', 'Debit'),
('Medicine', 'Debit'),
('Health Insurance', 'Debit'),
('Gym Membership', 'Debit'),
('Tuition', 'Debit'),
('Books & Supplies', 'Debit'),
('Online Courses', 'Debit'),
('School Fees', 'Debit'),
('Movies', 'Debit'),
('Subscriptions', 'Debit'),
('Events & Concerts', 'Debit'),
('Games', 'Debit'),
('Clothing', 'Debit'),
('Electronics', 'Debit'),
('Home Appliances', 'Debit'),
('Online Shopping', 'Debit'),
('Flights', 'Debit'),
('Hotels', 'Debit'),
('Travel Insurance', 'Debit'),
('Sightseeing', 'Debit'),

-- Investments
('Investments', 'Neutral'),
('Mutual Funds', 'Neutral'),
('Stocks', 'Neutral'),
('Real Estate', 'Neutral'),
('Gold', 'Neutral'),
('Cryptocurrency', 'Neutral'),

-- Transfers
('Transfers', 'Neutral'),
('Bank Transfer', 'Neutral'),
('Wallet Top-up', 'Neutral'),
('Loan to Friend', 'Neutral'),
('Transfer to Savings', 'Neutral'),

-- Loans
('Loans', 'Neutral'),
('Loan EMI Payment', 'Neutral'),
('Loan Received', 'Neutral'),
('Interest Paid', 'Neutral'),
('Interest Received', 'Neutral'),

-- Taxes & Fees
('Taxes & Fees', 'Debit'),
('Income Tax', 'Debit'),
('GST', 'Debit'),
('Late Fees', 'Debit'),
('Bank Charges', 'Debit'),

-- Charity & Donations
('Charity & Donations', 'Debit'),
('Religious Donations', 'Debit'),
('NGO Contributions', 'Debit'),
('Crowdfunding Support', 'Debit'),

-- Insurance
('Insurance', 'Debit'),
('Life Insurance', 'Debit'),
('Vehicle Insurance', 'Debit'),
('Home Insurance', 'Debit');
