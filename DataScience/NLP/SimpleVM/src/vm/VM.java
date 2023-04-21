package vm;

import static vm.Bytecode.*;

import java.util.List;
import java.util.ArrayList;

public class VM
{
	int[] globals;
	int[] code;
	int[] stack;
	
	int ip;
	int sp = -1;
	int fp;
	
	boolean trace = false;
	
	public VM(int[] code, int main, int dataSize)
	{
		this.code = code;
		this.ip	  = main;
		globals   = new int[dataSize];
		stack 	  = new int[100];
	}

	public void cpu()
	{	
		loop:
		while ( ip < code.length )
		{	
			int opCode = code[ip]; //fetch
			if ( trace ) System.err.printf("%-35s", this.disassemble());
			ip++;

			switch(opCode)
			{
				case ICONST:
					int v = code[ip];
					ip++;
					sp++;
					stack[sp] = v;
					break;
				case PRINT:
					v = stack[sp];
					sp--;
					//System.out.println(v);
					break;
				case GLOAD:
					int addr = code[ip];
					ip++;
					v = globals[addr];
					sp++;
					stack[sp] = v;
					break;
				case GSTORE:
					v = stack[sp];
					sp--;
					addr = code[ip];
					ip++;
					globals[addr] = v;
					break;
				case HALT:
					break loop;
				default:
					throw new Error("invalid opcode: " + opCode + " at ip="+ (ip-1) );
			}
			if ( trace ) System.err.println(this.stackString());
			opCode = code[ip];
		}

		if ( trace )
		{
			System.err.printf("%-35s", this.disassemble());
			System.err.println(this.stackString());
			this.dumpDataMemory();
		}
			
	}
	
	protected String stackString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("stack=[");
		for (int i = 0; i <= sp; i++)
		{
			int o = stack[i];
			buf.append(" ");
			buf.append(o);
		}
		buf.append(" ]");
		return buf.toString();
	}
	
	protected String disassemble()
	{
		if (ip >= code.length) return "";
		int opCode 		  = code[ip];
		String opName 	  = Bytecode.instructions[opCode].name;
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("%04d:\t%-11s", ip, opName));
		int nArgs = Bytecode.instructions[opCode].nOperands;
		if ( nArgs > 0 )
		{
			List<String> operands = new ArrayList<>();
			for (int i = ip + 1; i <= ip + nArgs; i++)
			{
				operands.add(String.valueOf(code[i]));
			}
			for (int i = 0; i < operands.size(); i++)
			{
				String s = operands.get(i);
				if ( i > 0 ) buf.append(", ");
				buf.append(s);
			}
		}
		return buf.toString();
	}
	
	protected void dumpDataMemory()
	{
		System.err.println("Data memory:");
		int addr = 0;
		for (int o : globals)
		{
			System.err.printf("%04d: %s\n", addr, o);
			addr++;
		}
		System.err.println();
	}
	
	protected void dumpCodeMemory()
	{
		System.err.println("Code Memory:");
		int addr = 0;
		for (int o : code)
		{
			System.err.printf("%04d %d\n", addr, o);
			addr++;
		}
		System.err.println();
	}
}
