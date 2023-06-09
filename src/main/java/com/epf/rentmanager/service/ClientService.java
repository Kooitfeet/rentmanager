package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service

public class ClientService {

	private ClientDao clientDao;
	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	
	
	public long create(Client client) throws ServiceException {
		try {
			return this.clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long delete(Client client) throws ServiceException {
		try {
			return this.clientDao.delete(client);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public Client findById(long id) throws ServiceException {
		try{
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int count() throws ServiceException {
		try {
			return findAll().size();
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}
	public long update(Client client) throws ServiceException {
		try {
			return this.clientDao.update(client);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
}
