package riscv

import chisel3._

class Main extends Module{
	val io = IO(new Bundle {
		val instruction = Output(UInt(32.W))
    		val AluOut = Output(SInt(32.W))
    		val branchCheck = Output(UInt(1.W))




})

		val control = Module(new Control())
		val immediate = Module(new Immediate())
		val alu = Module(new ALU())
		val alu_control = Module(new ALUcontrol())
		val register32 = Module(new Register32())
		val ins = Module(new InsMem())
		val Pc = Module(new PC())
		val jalr = Module(new JALR())
		val datamem = Module(new DataMem())
		val ins_fetch = Module(new IF_Pipeline()) // Pipeline 1
		val ins_decode = Module(new ID_Pipeline()) // Pipeline 2
		val Execute = Module(new Ex_Pipeline()) // Pipeline 3	
		val Mempipe = Module(new Ex_Pipeline()) // Pipeline 4

		//Data memory connections
		//datamem.io.store :=  control.io.MemWrite
		//datamem.io.load :=  control.io.MemRead
		//datamem.io.addr := (alu.io.out(9,2)).asUInt
		//datamem.io.store_data :=  register32.io.rs2


		//when(Execute.io.EX_MemToReg_Output === 0.U){
		//	register32.io.DataWrite := Execute.io.Ex_ALU_Output
		//}.elsewhen(Execute.io.EX_MemToReg_Output === 1.U){
		//	Mempipe.io.Ex_dataMem_dataOutput_Input := datamem.io.Data_Out
		//	register32.io.DataWrite := Mempipe.io.Ex_dataMem_dataOutput_Output	
		//}.otherwise{
		//	Mempipe.io.Ex_dataMem_dataOutput_Input := datamem.io.Data_Out
		//	register32.io.DataWrite := Mempipe.io.Ex_dataMem_dataOutput_Output
		//}
		//io.reg_output := register32.io.DataWrite




		//JALR connections
		//------------------done--------------------
		when(control.io.extend_sel === "b0".U){
			jalr.io.immediate := immediate.io.I_Type
		}.elsewhen(control.io.extend_sel === "b10".U){
			jalr.io.immediate := immediate.io.S_Type
		}.elsewhen(control.io.extend_sel === "b01".U){
			jalr.io.immediate := immediate.io.U_Type
		}.otherwise{
			jalr.io.immediate := DontCare
		}
		
		jalr.io.rs1 := register32.io.rs1
		// ----------------done------------------------
	

		//--------------Done-------------------------
		//PC and Instruction connection
		ins.io.wrAddr := Pc.io.pc(11,2)
		io.instruction := ins.io.rdData
		Pc.io.input := Pc.io.pc4
		//----------------Done----------------------

		//----------------Done---------------------
		//IF ID INput
		ins_fetch.io.ins_Input := io.instruction
		ins_fetch.io.pc4_Input := Pc.io.pc4
		ins_fetch.io.pc_Input := Pc.io.pc
		//----------------Done--------------------

		//--------------Done-------------------------
		//CONTROL Connection
		control.io.opcode := ins_fetch.io.ins_Output(6,0)

		
		//IMMEDIATE GENERATION Connection
		immediate.io.instruction := ins_fetch.io.ins_Output
		immediate.io.pc := ins_fetch.io.pc_Output
		//--------------------Done--------------------	



		//when((alu.io.branch.asUInt & ins_decode.io.branch_Output) === 1.U && ins_decode.io.Next_pc_sel_Output === 1.U){
		//	ins_fetch.io.pc_input  := immediate.io.Sb_Type.asUInt
		//}.elsewhen((alu.io.branch.asUInt & ins_decode.io.branch_Output) === 0.U && ins_decode.io.Next_pc_sel_Output === 1.U){
		//	ins_fetch.io.pc_input  := ins_fetch.io.pc_input + 4.U
		//}.elsewhen(ins_decode.io.Next_pc_sel_Output === 0.U){
		//	ins_fetch.io.pc_input  := ins_fetch.io.pc_input + 4.U
		//}.elsewhen(ins_decode.io.Next_pc_sel_Output === "b10".U){
		//	ins_fetch.io.pc_input := immediate.io.Uj_Type.asUInt
		//}.elsewhen(ins_decode.io.Next_pc_sel_Output === "b11".U){
		//	ins_fetch.io.pc_input := jalr.io.out.asUInt
		//}.otherwise{
		//	ins_fetch.io.pc_input := DontCare
		//}
				



		//--------------------Done-----------------------------
		//Register32 Connection
		register32.io.rs1_sel := ins_fetch.io.ins_Output(19,15)
		register32.io.rs2_sel := ins_fetch.io.ins_Output(24,20)
		//register32.io.rd_sel := ins_fetch.io.ins_Output(11,7)
		//register32.io.RegWrite := Execute.io.EX_RegWrite_Output
		//--------------------Done-------------------------------

		

		//-------------------Done-------------------------------
		//ALU CONTROL Connection
		alu_control.io.ALUop := Control.io.ALUoperation
		alu_control.io.func3 := ins_fetch.io.ins_Output(14,12) //ins.io.rdData(14,12)
		alu_control.io.func7 := ins_fetch.io.ins_Output(30) //ins.io.rdData(30)
		//-------------------Done-------------------------------


		//-----------------Done----------------------
		//ALU Connection
		when(control.io.operand_A_sel === 0.U || control.io.operand_A_sel === 3.U){
			ins_decode.io.OperandA_Input := register32.io.rs1
		}.elsewhen(control.io.operand_A_sel === 2.U{
			ins_decode.io.OperandA_Input := ins_fetch.io.pc4_Output.asSInt
		}.otherwise{
			ins_decode.io.OperandA_Input := DontCare
		}


		when(control.io.operand_B_sel === 0.U){
			ins_decode.io.OperandB_Input := register32.io.rs2			
			//alu.io.b := ins_decode.io.imm_Output
		}.elsewhen(control.io.operand_B_sel === 1.U){
			when(control.io.extend_sel === 0.U){
				ins_decode.io.OperandB_Input := immediate.io.I_Type
			}.elsewhen(control.io.extend_sel === 1.U){
				ins_decode.io.OperandB_Input := immediate.io.U_Type
			}.elsewhen(control.io.extend_sel === 2.U){
				ins_decode.io.OperandB_Input := immediate.io.S_Type
			}.otherwise{
				ins_decode.io.OperandB_Input := register32.io.rs2
			}
		}.otherwise{
			ins_decode.io.OperandB_Input := register.io.rs2
		}
		//---------------Done-----------------------

		
		//alu.io.ALUcont := alu_control.io.ALUcont
		//register32.io.DataWrite := alu.io.out
		//io.reg_output := register32.io.rs1
		//io.reg_output := alu.io.out

		//---------------Done-------------------------
		//ID and EXE Pipeline Connection
		ins_decode.io.MemWrite_Input := control.io.MemWrite
  		ins_decode.io.MemRead_Input := control.io.MemRead
  		ins_decode.io.MemtoReg_Input := control.io.MemtoReg
  		ins_decode.io.rd_Input := ins_fetch.io.ins_Output(11,7)
  		ins_decode.io.strData_Input := register32.io.rs2
  		ins_decode.io.aluCtrl_Input := alu_control.io.ALUop
  		ins_decode.io.RegWrite_Input := control.io.RegWrite
		//------------------Done----------------------------


		//----------------Done----------------------------
		//----------------Execution------------------------
		alu.io.ALUcont := ins_decode.io.aluCtrl_Output
  		alu.io.a := ins_decode.io.OperandA_Input
  		alu.io.b := ins_decode.io.OperandB_Input
  		Execute.io.EX_alu_Output_input := alu.io.out
  		Execute.io.Ex_Alu_branch_output_Input := alu.io.branch
  		io.AluOut := Execute.io.Ex_ALU_Output_Output //alu.io.aluOut
  		io.branchCheck := Execute.io.Ex_Alu_branch_Output_Output //alu.io.branch
		//---------------Done--------------------------------
		
		//-----------------Done----------------------------------
		//EXE and MEM Pipeline Input
		Execute.io.EX_MemWrite_Input := ins_decode.io.MemWrite_Output
  		Execute.io.EX_MemRead_Input := ins_decode.io.MemRead_Output
  		Execute.io.EX_MemToReg_Input := ins_decode.io.MemtoReg_Output
  		Execute.io.EX_rd_Input := ins_decode.io.rd_Output
  		Execute.io.EX_strData_Input := ins_decode.io.strData_Output
  		Execute.io.Ex_ALU_output_Input := alu.io.out
  		Execute.io.EX_RegWrite_Input := ins_decode.io.RegWrite_Output
		//-------------------------Done----------------------------
		

		//-------------------Done---------------------------------
		//Data_Mem Connection
		datamem.io.store := Execute.io.Ex_MemWrite_Output
  		datamem.io.load := Execute.io.Ex_MemRead_Output
  		datamem.io.addr := Execute.io.Ex_ALU_Output_Output(9,2).asUInt
  		datamem.io.store_data := Execute.io.Ex_strData_Output //reg.io.rs2
  		when(control.io.MemtoReg === 0.U){
    			register32.io.DataWrite := io.AluOut
  		}.elsewhen(control.io.MemtoReg === 1.U){
    			register32.io.DataWrite := datamem.io.Data_Out
  		}.otherwise{
    			register32.io.DataWrite := datamem.io.Data_Out
  		}
		//-----------------Done---------------------------------
	
		//-------------------Done-------------------------------
		//Mem_Write_Back inputs
		Mempipe.io.Data_MemToReg_Input := Execute.io.Ex_MemToReg_Output
  		Mempipe.io.Data_rd_Input := Execute.io.Ex_rd_Output
  		Mempipe.io.Data_ALU_output_Input := Execute.io.Ex_ALU_Output_Output
  		Mempipe.io.Data_dataOutput_Input := datamem.io.Data_Out
  		Mempipe.io.Data_RegWrite_Input := Execute.io.Ex_RegWrite_Output
		//-----------------------Done---------------------------
		
		
		register32.io.rd_sel := Mempipe.io.Data_rd_Output
  		register32.io.RegWrite := Mempipe.io.Data_RegWrite_Output

  		register32.io.rd_sel := Mempipe.io.Data_rd_Output
  		register32.io.RegWrite := Mempipe.io.Data_RegWrite_Output

  		when(Mempipe.io.Data_MemToReg_Output === 1.U){
    			register32.io.DataWrite :=  Mempipe.io.Data_dataOutput_Output
  		}.otherwise{
    			when(register32.io.RegWrite === 1.U){
      				register32.io.DataWrite := Mempipe.io.Data_ALU_output_Output
    			}.otherwise{
      				register32.io.DataWrite := 0.S
    			}
  		}
		


}
