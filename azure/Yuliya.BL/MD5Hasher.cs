using System.Security.Cryptography;
using System.Text;

namespace Yuliya.BL
{
    public class MD5Hasher
    {
        public static byte[] Hash(string text)
        {
            using (var sha1 = new SHA1CryptoServiceProvider())
            {
                var buffer = Encoding.UTF8.GetBytes(text);
                return sha1.ComputeHash(buffer);
            }
        }
    }
}