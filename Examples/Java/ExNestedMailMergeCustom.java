//////////////////////////////////////////////////////////////////////////
// Copyright 2001-2011 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package Examples;

import com.aspose.words.*;
import org.testng.annotations.Test;

import java.util.ArrayList;


public class ExNestedMailMergeCustom extends ExBase
{
    @Test
    public void MailMergeCustomDataSource() throws Exception
    {
        // Create some data that we will use in the mail merge.
        CustomerList customers = new CustomerList();
        customers.add(new Customer("Thomas Hardy", "120 Hanover Sq., London"));
        customers.add(new Customer("Paolo Accorti", "Via Monte Bianco 34, Torino"));

        // Create some data for nesting in the mail merge.
        customers.get(0).getOrders().add(new Order("Rugby World Cup Cap", 2));
        customers.get(0).getOrders().add(new Order("Rugby World Cup Ball", 1));
        customers.get(1).getOrders().add(new Order("Rugby World Cup Guide", 1));

        // Open the template document.
        Document doc = new Document(getMyDir() + "NestedMailMerge.CustomDataSource.doc");

        // To be able to mail merge from your own data source, it must be wrapped
        // into an object that implements the IMailMergeDataSource interface.
        CustomerMailMergeDataSource customersDataSource = new CustomerMailMergeDataSource(customers);

        // Now you can pass your data source into Aspose.Words.
        doc.getMailMerge().executeWithRegions(customersDataSource);

        doc.save(getMyDir() + "NestedMailMerge.CustomDataSource Out.doc");
    }

    /**
     * An example of a "data entity" class in your application.
     */
    public class Customer
    {
        public Customer(String aFullName, String anAddress)
        {
            mFullName = aFullName;
            mAddress = anAddress;
            mOrders = new OrderList();
        }
        public String getFullName()
        {
            return mFullName;
        }
        public void setFullName(String value)
        {
            mFullName = value;
        }
        public String getAddress()
        {
            return mAddress;
        }
        public void setAddress(String value)
        {
            mAddress = value;
        }

        public OrderList getOrders()
        {
            return mOrders;
        }
        public void setOrders(OrderList value)
        {
            mOrders = value;
        }

        private String mFullName;
        private String mAddress;
        private OrderList mOrders;
    }

    /**
     * An example of a typed collection that contains your "data" objects.
     */
    public class CustomerList extends ArrayList
    {
        public Customer get(int index)
        {
            return (Customer)super.get(index);
        }

        public void set(int index, Customer value)
        {
            super.set(index, value);
        }
    }

    /**
     * An example of a child "data entity" class in your application.
     */
    public class Order
    {
        public Order(String oName, int oQuantity)
        {
            mName = oName;
            mQuantity = oQuantity;
        }

        public String getName()
        {
            return mName;
        }

        public void setName(String value)
        {
            mName = value;
        }

        public int getQuantity()
        {
            return mQuantity;
        }

        public void setName(int value)
        {
            mQuantity = value;
        }

        private String mName;
        private int mQuantity;
    }

    /**
     * An example of a typed collection that contains your "data" objects.
     */
    public class OrderList extends ArrayList
    {
        public Order get(int index)
        {
            return (Order)super.get(index);
        }

        public void set(int index, Order value)
        {
            super.set(index, value);
        }
    }

    /**
     * A custom mail merge data source that you implement to allow Aspose.Words
     * to mail merge data from your Customer objects into Microsoft Word documents.
     */
    public class CustomerMailMergeDataSource implements IMailMergeDataSource
    {
        public CustomerMailMergeDataSource(CustomerList customers)
        {
            mCustomers = customers;

            // When the data source is initialized, it must be positioned before the first record.
            mRecordIndex= -1;
        }

        /**
         * The name of the data source. Used by Aspose.Words only when executing mail merge with repeatable regions.
         */
        public String getTableName()
        {
            return "Customer";
        }

        /**
         * Aspose.Words calls this method to get a value for every data field.
         */
        public boolean getValue(String fieldName, Object[] fieldValue)
        {
            if(fieldName.equals("FullName"))
            {
                fieldValue[0] = mCustomers.get(mRecordIndex).getFullName();
                return true;
            }
            else if (fieldName.equals("Address"))
            {
                fieldValue[0] = mCustomers.get(mRecordIndex).getAddress();
                return true;
            }
            else if(fieldName.equals("Order"))
            {
                fieldValue[0] = mCustomers.get(mRecordIndex).getOrders();
                return true;
            }
            else
            {
                // A field with this name was not found,
                // return false to the Aspose.Words mail merge engine.
                fieldValue = null;
                return false;
            }
        }

        /**
         * A standard implementation for moving to a next record in a collection.
         */
        public boolean moveNext()
        {
            if (!isEof())
                mRecordIndex++;

            return (!isEof());
        }

        //ExStart
        //ExId:GetChildDataSourceExample
        //ExSummary:Shows how to get a child collection of objects by using the GetChildDataSource method in the parent class.
        public IMailMergeDataSource getChildDataSource(String tableName)
        {
            // Get the child collection to merge it with the region provided with tableName variable.
            if(tableName.equals("Order"))
                return new OrderMailMergeDataSource(mCustomers.get(mRecordIndex).getOrders());
            else
                return null;
        }
        //ExEnd

        private boolean isEof()
        {
            return (mRecordIndex >= mCustomers.size());
        }

        private CustomerList mCustomers;
        private int mRecordIndex;
    }

    public class OrderMailMergeDataSource implements IMailMergeDataSource
    {
        public OrderMailMergeDataSource(OrderList orders)
        {
            mOrders = orders;

            // When the data source is initialized, it must be positioned before the first record.
            mRecordIndex = -1;
        }

        /**
         * The name of the data source. Used by Aspose.Words only when executing mail merge with repeatable regions.
         */
        public String getTableName()
        {
            return "Order";
        }

        /**
         * Aspose.Words calls this method to get a value for every data field.
         */
        public boolean getValue(String fieldName, Object[] fieldValue)
        {
            if(fieldName.equals("Name"))
            {
                fieldValue[0] = mOrders.get(mRecordIndex).getName();
                return true;
            }
            else if(fieldName.equals("Quantity"))
            {
                fieldValue[0] = mOrders.get(mRecordIndex).getQuantity();
                return true;
            }
            else
            {
                // A field with this name was not found,
                // return false to the Aspose.Words mail merge engine.
                fieldValue = null;
                return false;
            }
        }

        /**
         * A standard implementation for moving to a next record in a collection.
         */
        public boolean moveNext()
        {
            if (!isEof())
                mRecordIndex++;

            return (!isEof());
        }

        // Return null because we haven't any child elements for this sort of object.
        public IMailMergeDataSource getChildDataSource(String tableName)
        {
            return null;
        }

        private boolean isEof()
        {
            return (mRecordIndex >= mOrders.size());
        }

        private OrderList mOrders;
        private int mRecordIndex;
    }
}