package org.mercosur.fondoPrevision.cargadedatos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mercosur.fondoPrevision.cargadedatos.entities.PrestamosInterface;
import org.mercosur.fondoPrevision.cargadedatos.entities.SaldosInterface;
import org.mercosur.fondoPrevision.cargadedatos.entities.SueldosInterface;
import org.mercosur.fondoPrevision.cargadedatos.repository.PrestamosInterfaceRepository;
import org.mercosur.fondoPrevision.cargadedatos.repository.SaldosInterfaceRepository;
import org.mercosur.fondoPrevision.cargadedatos.repository.SueldosInterfaceRepository;
import org.mercosur.fondoPrevision.entities.Gcargo;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.GvinculoFuncionarioCargo;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.SaldoPrestamosAcum;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SueldoMes;
import org.mercosur.fondoPrevision.entities.TipoMovimiento;
import org.mercosur.fondoPrevision.entities.TipoPrestamo;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.GvinculoFuncionarioCargoRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.PrestamoRepository;
import org.mercosur.fondoPrevision.repository.SaldoPrestamosAcumRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.repository.SueldoMesRepository;
import org.mercosur.fondoPrevision.repository.TipoMovimientoRepository;
import org.mercosur.fondoPrevision.repository.TipoPrestamoRepository;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargaDatosServiceImpl implements CargaDatosService{

	@Autowired
	SaldosInterfaceRepository saldosInterfaceRepository;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	SueldosInterfaceRepository sueldosInterfaceRepository;
	
	@Autowired
	SueldoMesRepository sueldoMesRepository;
	
	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;
	
	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	PrestamosInterfaceRepository prestamosInterfaceRepository;
	
	@Autowired
	TipoPrestamoRepository tipoPrestamoRepository;
	
	@Autowired
	PrestamoRepository prestamoRepository;
	
	@Autowired
	SaldoPrestamosAcumRepository saldoPrestamosAcumRepository;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	GvinculoFuncionarioCargoRepository gvinculofunCargoRepository;
	
	
	@Override
	public List<Saldos> cargaSaldosDesde(String nomarchivo) throws Exception {
		List<SaldosInterface> lstinter = saldosInterfaceRepository.findAll();
		
		String mesliquid = lstinter.get(0).getMesliquidacion();
		List<Saldos> lstSaldos = saldosRepository.getByMesLiquidacion(mesliquid);
		if(lstSaldos.isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			Date fecha = calendar.getTime();
			for(SaldosInterface si : lstinter) {
				Gplanta funcionario = gplantaRepository.findByTarjeta(si.getTarjeta());
				Saldos saldos = new Saldos();
				saldos.setFecha(fecha);
				saldos.setCapitalDispActual(si.getCapitalDispActual());
				saldos.setCapitalDispAntes(si.getCapitalDispAntes());
				saldos.setCapitalIntegActual(si.getCapitalIntegActual());
				saldos.setCapitalIntegAntes(si.getCapitalIntegAntes());
				saldos.setNumerales(si.getCapitalIntegActual());
				saldos.setTarjeta(si.getTarjeta());
				saldos.setMesliquidacion(si.getMesliquidacion());
				saldos.setFuncionario(funcionario);
				saldos = saldosRepository.save(saldos);
				lstSaldos.add(saldos);
			}
		}
		else {
			throw new Exception("Ya existen registros con el mes liquidación!");
		}
		return lstSaldos;
	}

	@Override
	public List<SueldoMes> cargaSueldosDesde(String nomarchivo) throws Exception {
		List<SueldoMes> lstSueldos = new ArrayList<SueldoMes>();
		List<SueldosInterface> lstinter = sueldosInterfaceRepository.findAll();
		
		String mesliquid = "202006";
		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();
		for(SueldosInterface si : lstinter) {
			Gplanta funcionario = gplantaRepository.findByTarjeta(si.getTarjeta());
			SueldoMes sm = new SueldoMes();
			sm.setAniomes(mesliquid);
			sm.setComplemento(si.getComplemento());
			sm.setFecha(fecha);
			sm.setFuncionario(funcionario);
			sm.setMotivo("retribución mensual para Aguinaldo");
			sm.setSueldomes(si.getSueldo());
			sm.setTarjeta(funcionario.getTarjeta());
			sm = sueldoMesRepository.save(sm);
			
			lstSueldos.add(sm);
		}
		return null;
	}

	@Override
	public List<Movimientos> cargaMovimientos(String nomarchivo) throws Exception {
		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();
		List<Saldos> lstSaldos = saldosRepository.findAll();
		String mesliq = lstSaldos.get(0).getMesliquidacion();
		
		for(Saldos s : lstSaldos) {
			Gplanta fun = gplantaRepository.getOne(s.getFuncionario().getIdgplanta());
			TipoMovimiento tpm = tipoMovimientoRepository.getByIdftipomovimiento(1);
			Movimientos mov = new Movimientos();
			mov.setTarjeta(fun.getTarjeta());
			mov.setTipoMovimiento(tpm);
			mov.setCodigoMovimiento((short) 1);
			mov.setFechaMovimiento(fecha);
			mov.setFuncionario(fun);
			mov.setImporteCapSec(BigDecimal.ZERO);
			mov.setImporteIntFunc(BigDecimal.ZERO);
			mov.setImporteMov(s.getCapitalDispActual());
			mov.setMesliquidacion(mesliq);
			mov.setObservaciones("primera carga de datos");
			mov.setSaldoAnterior(BigDecimal.ZERO);
			mov.setSaldoActual(s.getCapitalDispActual());
			mov = movimientosRepository.save(mov);
		}
		
		List<Movimientos> lstMovs = movimientosRepository.findAll();
		return lstMovs;
	}

	@Override
	public List<Prestamo> cargaPrestamos(String nomarchivo) throws Exception {
		List<PrestamosInterface> interLst = prestamosInterfaceRepository.findAll();
		try {
			for(PrestamosInterface pi : interLst) {
				Gplanta fun = gplantaRepository.findByTarjeta(pi.getTarjeta());
				TipoPrestamo tprst = tipoPrestamoRepository.getBycodigoPrestamo((short)1);
				Prestamo prst = new Prestamo();
				prst.setFuncionario(fun);
				prst.setTarjeta(pi.getTarjeta());
				prst.setNroprestamo(pi.getNroprestamo());
				prst.setCantCuotas(pi.getPlazo());
				prst.setCapitalPrestamo(pi.getCapital());
				prst.setCodigoPrestamo((short)1);
				prst.setTipoPrestamo(tprst);
				prst.setCuota(pi.getCuota());
				prst.setCuotasPagas(pi.getCuotaspagas());
				prst.setFechaPrestamo(pi.getFechaprestamo());
				prst.setInteresPrestamo(pi.getInteres());
				prst.setSaldoPrestamo(pi.getSaldo());
				prst = prestamoRepository.save(prst);
			}
			List<Prestamo> lstPrst = prestamoRepository.findAll();
			return lstPrst;			
		}
		catch(Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<SaldoPrestamosAcum> actualizaSaldosPrst() throws Exception {
		List<SaldoPrestamosAcum> lstResult = new ArrayList<SaldoPrestamosAcum>();
		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();
		List<Long> lstIds = prestamoRepository.findIdGplantaDistinct();
		for(Long id:lstIds){
			BigDecimal suma = BigDecimal.ZERO;
			Gplanta one = gplantaRepository.getOne(id);
			for(Prestamo p: one.getLstPrsts()) {
				suma = suma.add(p.getSaldoPrestamo());
			}
			SaldoPrestamosAcum spa = new SaldoPrestamosAcum();
			spa.setFuncionario(one);
			spa.setFecha(fecha);
			spa.setSaldoPrestAcumulado(suma);
			spa.setTarjeta(one.getTarjeta());
			spa = saldoPrestamosAcumRepository.save(spa);
			
			lstResult.add(spa);
		}
		return lstResult;
	}

	@Override
	public List<Gplanta> creaVinculoConCargo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Gplanta> lstPlanta = (List<Gplanta>) gplantaService.getAllPlanta();
		Date fi = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		fi = sdf.parse("01/01/2019");
		for(Gplanta fun : lstPlanta) {
			Gcargo cargo = fun.getGcargo();
			GvinculoFuncionarioCargo gvfc = new GvinculoFuncionarioCargo();
			gvfc.setGcargos_id(cargo.getIdgcargo());
			gvfc.setGplanta_id(fun.getIdgplanta());
			gvfc.setTarjeta(fun.getTarjeta());
			if(fun.getIngreso() != null) {
				gvfc.setFecha_inicial(fun.getIngreso());
			}
			else {
				gvfc.setFecha_inicial(fi);
			}
			gvfc = gvinculofunCargoRepository.save(gvfc);
		}
        System.out.println("Process exitValue: OK" );

		return lstPlanta;
	}

}
