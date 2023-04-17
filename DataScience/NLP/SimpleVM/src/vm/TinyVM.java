package vm;

import static vm.Bytecode.*;

public class TinyVM
{
	static int[] hello = {
		ICONST, 99,
		GSTORE, 0,
		GLOAD,  0,
		PRINT,
		HALT
	};
	
	public static void main(String[] args)
	{
		int dataSize = 1;
		int mainIp   = 0;
		VM vm    = new VM(hello, mainIp, dataSize);
		vm.trace = true;
		vm.cpu();
	}

}
